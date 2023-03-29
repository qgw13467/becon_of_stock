package com.ssafy.beconofstock.backtest.service;


import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.backtest.dto.BacktestResultDto;
import com.ssafy.beconofstock.backtest.dto.ChangeRateDto;
import com.ssafy.beconofstock.backtest.dto.YearMonth;
import com.ssafy.beconofstock.backtest.entity.InterestRate;
import com.ssafy.beconofstock.backtest.entity.Trade;
import com.ssafy.beconofstock.backtest.repository.FinanceRepository;
import com.ssafy.beconofstock.backtest.repository.InterestRateRepository;
import com.ssafy.beconofstock.backtest.repository.TradeRepository;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.repository.IndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.Temporal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BacktestServiceImpl implements BacktestService {

    private final TradeRepository tradeRepository;
    private final FinanceRepository financeRepository;
    private final IndicatorRepository indicatorRepository;
    private final InterestRateRepository interestRateRepository;

    @Override
    public BacktestResultDto getBacktestResult(BacktestIndicatorsDto backtestIndicatorsDto) {

        BacktestResultDto result = new BacktestResultDto();

        //각 기간에서의 변화량
        List<ChangeRateDto> changeRateDtos = new ArrayList<>();
        List<Double> changeRates = new ArrayList<>();


        //기간동안 매수가 생기는 연도,월
        List<YearMonth> rebalanceYearMonth = getRebalanceYearMonth(backtestIndicatorsDto);
        //입력받은 지표들
        List<Indicator> indicators = indicatorRepository.findByIdIn(backtestIndicatorsDto.getIndicators());

        for (YearMonth yearMonth : rebalanceYearMonth) {

            //TODO 입력받은 산업군에 포함된 회사의 Trade만 가져오도록 고칠것
            //이번 분기 매수가 가능한 회사목록
            List<Trade> trades = tradeRepository.findByYearAndMonth(yearMonth.getYear(), yearMonth.getMonth());

            //이번분기 전략에서 구매할 회사
            List<Trade> buyList = calcTradesIndicator(trades, indicators, backtestIndicatorsDto.getMaxStocks());

            //이번분기 전략
            Double revenue = getRevenue(yearMonth, buyList, backtestIndicatorsDto.getRebalance());

            changeRateDtos.add(new ChangeRateDto(revenue, yearMonth.getYear(), yearMonth.getMonth()));
            changeRates.add(revenue);
        }

        //누적수익률 (단위 : % )
        List<ChangeRateDto> cumulativeReturn = getCumulativeReturn(changeRateDtos, backtestIndicatorsDto.getFee());
        result.setStrategyValues(cumulativeReturn);

        //전략 지표들 계산
        result.setStrategyCumulativeReturn(cumulativeReturn.get(cumulativeReturn.size() - 1).getChageRate());
        result.setStrategyCagr(getAvg(changeRates));
        result.setStrategySharpe(getSharpe(changeRateDtos, changeRates, backtestIndicatorsDto));
        result.setStrategySortino(getSortino(changeRateDtos, changeRates, backtestIndicatorsDto));
        result.setStrategyRevenue(winCount(changeRates));
        result.setStrategyMDD(getMdd(cumulativeReturn));


        return result;
    }


    private Double getMdd(List<ChangeRateDto> cumulativeReturn) {
        double mdd = 100D;
        double max = 0D;
        double min = 99999999D;
        for (ChangeRateDto changeRateDto : cumulativeReturn) {
            if (max < changeRateDto.getChageRate()) {
                max = changeRateDto.getChageRate();
                min = 99999999D;
                continue;
            }

            if (min > changeRateDto.getChageRate()) {
                min = changeRateDto.getChageRate();
                double temp = (min - max) / max;
                if (mdd > temp) {
                    mdd = temp;
                }
            }
        }
        return mdd;
    }


    //리벨런스 횟수 계산
    private int getRebalanceCount(int startYear, int startMonth,
                                  int endYear, int endMonth, int rebalance) {
        int result = 0;
        result = endMonth - startMonth + (endYear - startYear) * 12;
        result = result / rebalance;
        int temp = result % rebalance;
        if (temp != 0) {
            result++;
        }
        return result;
    }


    //샤프지수 계산
    private Double getSharpe(List<ChangeRateDto> changeRateDtos, List<Double> changeRate, BacktestIndicatorsDto backtestIndicatorsDto) {

        Double deviation = getDeviation(changeRate);
        Double result = getRevenueMinusInterest(changeRateDtos, changeRate, backtestIndicatorsDto);
        result = 1.0 * result / deviation;
        return result;
    }

    //소티노 계산
    private Double getSortino(List<ChangeRateDto> changeRateDtos, List<Double> changeRate, BacktestIndicatorsDto backtestIndicatorsDto) {
        Double nagativeDeviation = getNagativeDeviation(changeRate);
        Double result = getRevenueMinusInterest(changeRateDtos, changeRate, backtestIndicatorsDto);
        result = 1.0 * result / nagativeDeviation;
        return result;
    }

    //샤프,소티노 공통부분 분리
    private Double getRevenueMinusInterest(List<ChangeRateDto> changeRateDtos, List<Double> changeRate, BacktestIndicatorsDto backtestIndicatorsDto) {
        List<InterestRate> interestRates =
                interestRateRepository.findByYearMonthList(
                        backtestIndicatorsDto.getStartYear(),
                        backtestIndicatorsDto.getEndYear());

        Double avgRevenue = getAvg(changeRate);

        List<Double> interests = new ArrayList<>();

        for (ChangeRateDto changeRateDto : changeRateDtos) {
            InterestRate interestRate =
                    getInterestRateByYearMonth(interestRates, changeRateDto.getYear(), changeRateDto.getMonth());
            interests.add(interestRate.getInterestRate());
        }
        Double avgInterest = getAvg(interests);

        return avgRevenue - avgInterest;
    }

    //기간동안 매수가 생기는 연도,월 반환
    private List<YearMonth> getRebalanceYearMonth(int startYear, int startMonth,
                                                  int endYear, int endMonth, int rebalance) {
        List<YearMonth> result = new ArrayList<>();
        int thisYear = startYear;
        int thisMonth = startMonth;
        for (int start = 0; start < getRebalanceCount(startYear, startMonth, endYear, endMonth, rebalance); start++) {
            thisMonth += rebalance;
            if (thisMonth > 12) {
                thisYear++;
                thisMonth = thisMonth - 12;
            }
            result.add(new YearMonth(thisYear, thisMonth));
        }
        return result;
    }

    //기간동안 매수가 생기는 연도,월 반환
    private List<YearMonth> getRebalanceYearMonth(BacktestIndicatorsDto backtestIndicatorsDto) {
        Integer startYear = backtestIndicatorsDto.getStartYear();
        Integer startMonth = backtestIndicatorsDto.getStartMonth();
        Integer endYear = backtestIndicatorsDto.getEndYear();
        Integer endMonth = backtestIndicatorsDto.getEndMonth();
        Integer rebalance = backtestIndicatorsDto.getRebalance();
        return getRebalanceYearMonth(startYear, startMonth, endYear, endMonth, rebalance);
    }

    //누적 수익률 계산
    private List<ChangeRateDto> getCumulativeReturn(List<ChangeRateDto> changeRateDtos, double fee) {
        List<ChangeRateDto> result = new ArrayList<>();
        double now = 100D;
        for (ChangeRateDto chageRateDto : changeRateDtos) {
            now = now * chageRateDto.getChageRate() * (100.0 - fee);
            result.add(new ChangeRateDto(now, chageRateDto.getYear(), chageRateDto.getMonth()));
        }
        return result;
    }

    //기간평균수익률
    private Double getAvg(List<Double> changeRate) {
        Double sum = 0D;
        for (Double rate : changeRate) {
            sum += rate;
        }
        return 1.0 * sum / changeRate.size();
    }


    //분산계산
    private Double getDeviation(List<Double> list) {
        Double avg = getAvg(list);
        Double sum = 0D;
        for (Double rate : list) {
            Double temp = rate - avg;
            sum += temp * temp;
        }
        sum = 1.0 * sum / list.size();
        return Math.sqrt(sum);
    }

    private Double getNagativeDeviation(List<Double> list) {
        Double avg = getAvg(list);
        Double sum = 0D;
        for (Double rate : list) {
            if (rate >= 0) {
                continue;
            }
            Double temp = rate - avg;
            sum += temp * temp;
        }
        sum = 1.0 * sum / list.size();
        return Math.sqrt(sum);
    }


    //양수 카운트
    private Integer winCount(List<Double> chageRate) {
        int sum = 0;
        for (Double rate : chageRate) {
            if (rate >= 0) {
                sum++;
            }
        }
        return sum;
    }

    //배열에서 해당 연월의 금리 구하기
    private InterestRate getInterestRateByYearMonth(List<InterestRate> list, Integer year, Integer month) {
        InterestRate result = null;
        for (InterestRate interestRate : list) {
            if (interestRate.getYear().equals(year) && interestRate.getMonth().equals(month)) {
                result = interestRate;
                break;
            }
        }
        return result;
    }

    //산업으로 회사찾기
    private List<String> getCorpByIndustry(List<String> industries) {
        return new ArrayList<>();
    }


    // TODO : 각 지표 정렬, 합산 수행
    private List<Trade> calcTradesIndicator(List<Trade> trades, List<Indicator> indicators, int maxNum) {

        for (Indicator indicator : indicators) {
            trades.sort(sortByIndicator(indicator));
            calSumTradesIndicator(trades, indicator);
        }
        calAverageRanking(trades);
        trades.sort(sortByIndicator(new Indicator("ranking")));

//        maxnum만큼 잘라서 반환
        if (maxNum > trades.size()) {
            maxNum = trades.size();
        }
        trades = trades.subList(0, maxNum);

        return trades;
    }

    private void calAverageRanking(List<Trade> trades) {
        for (Trade trade : trades) {
            trade.setRanking(trade.getRanking() / trade.getCnt());
        }
    }

    private void calSumTradesIndicator(List<Trade> trades, Indicator indicator) {

        int idx = 0;
        switch (indicator.getTitle()) {
            case "pricePER":
                for (int i = 0; i < trades.size(); i++) {
                    if (trades.get(i).getPricePER() > 0) {
                        idx = i;
                        break;
                    }
                }
                break;
            case "pricePBR":
                for (int i = 0; i < trades.size(); i++) {
                    if (trades.get(i).getPricePBR() > 0) {
                        idx = i;
                        break;
                    }
                }
                break;
            case "pricePSR":
                for (int i = 0; i < trades.size(); i++) {
                    if (trades.get(i).getPricePSR() > 0) {
                        idx = i;
                        break;
                    }
                }
                break;
            case "pricePOR":
                for (int i = 0; i < trades.size(); i++) {
                    if (trades.get(i).getPricePOR() > 0) {
                        idx = i;
                        break;
                    }
                }
                break;
        }

        for (int i = idx, j = 1; i < trades.size(); i++, j++) {
            trades.get(i).addRanking(j);
            trades.get(i).addCnt();
        }

    }

    private Comparator<Trade> sortByIndicator(Indicator indicator) {
        switch (indicator.getTitle()) {
            case "pricePER":
                return Comparator.comparing(Trade::getPricePER);
            case "pricePBR":
                return Comparator.comparing(Trade::getPricePBR);
            case "pricePSR":
                return Comparator.comparing(Trade::getPricePSR);
            case "pricePOR":
                return Comparator.comparing(Trade::getPricePOR);
            case "qualityROE":
                return Comparator.comparing(Trade::getQualityROE).reversed();
            case "qualityROA":
                return Comparator.comparing(Trade::getQualityROA).reversed();
            case "growth3MonthTake":
                return Comparator.comparing(Trade::getGrowth3MonthTake).reversed();
            case "growth3MonthOperatingProfit":
                return Comparator.comparing(Trade::getGrowth3MonthOperatingProfit).reversed();
            case "growth3MonthNetProfit":
                return Comparator.comparing(Trade::getGrowth3MonthNetProfit).reversed();
            default:
                return Comparator.comparing(Trade::getRanking);
        }

    }

    //구매한 종목이 한주기 다음 수익이 얼마인지 계산
    private Double getRevenue(YearMonth yearMonth, List<Trade> list, int rebalance) {
        Double result = 0D;
        List<Double> dist = new ArrayList<>();

        int useYear = yearMonth.getYear();
        int useMonth = yearMonth.getMonth();

        useMonth += rebalance;
        if (useMonth > 12) {
            useYear++;
            useMonth -= 12;
        }
        List<String> corcodes = list.stream().map(Trade::getCorcode).collect(Collectors.toList());

        List<Trade> byYearAndMonthAndCorcodeList = tradeRepository.findByYearAndMonthAndCorcodeList(useYear, useMonth, corcodes);

        for (Trade trade : list) {
            Trade find = findByCorcode(trade.getCorname(), byYearAndMonthAndCorcodeList);
            if (find == null) {
                dist.add(0D);
                continue;
            }
            dist.add((1.0 * find.getCorclose() / trade.getCorclose()));
        }
        result = getAvg(dist);
        return result;
    }

    private Trade findByCorcode(String corcode, List<Trade> trades) {
        Trade result = null;
        for (Trade trade : trades) {
            if (trade.getCorcode().equals(corcode)) {
                result = trade;
                break;
            }
        }
        return result;
    }
}
