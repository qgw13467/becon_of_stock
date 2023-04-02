package com.ssafy.beconofstock.backtest.service;


import com.ssafy.beconofstock.backtest.dto.*;
import com.ssafy.beconofstock.backtest.entity.Industry;
import com.ssafy.beconofstock.backtest.entity.InterestRate;
import com.ssafy.beconofstock.backtest.entity.Kospi;
import com.ssafy.beconofstock.backtest.entity.Trade;
import com.ssafy.beconofstock.backtest.repository.*;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.repository.IndicatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class BacktestServiceImpl implements BacktestService {

    private final TradeRepository tradeRepository;
    private final FinanceRepository financeRepository;
    private final IndicatorRepository indicatorRepository;
    private final BackIndustryRepository backIndustryRepository;
    private final InterestRateRepository interestRateRepository;
    private final KospiRepository kospiRepository;

    @Override
    public BacktestResultDto getBacktestResult(BacktestIndicatorsDto backtestIndicatorsDto) {

        BacktestResultDto result = new BacktestResultDto();

        //각 기간에서의 변화량
        List<ChangeRateDto> changeRateDtos = new ArrayList<>();
        List<Double> changeRates = new ArrayList<>();
        List<List<Double>> distHistory = new ArrayList<>();
        List<List<BuySellDto>> history = new ArrayList<>();

        //기간동안 매수가 생기는 연도,월
        List<YearMonth> rebalanceYearMonth = getRebalanceYearMonth(backtestIndicatorsDto);
        //시장 리벨런싱별 수익률
        List<ChangeRateDto> marketChangeRateDtos = getKospiList(backtestIndicatorsDto, rebalanceYearMonth);
//        for (int i = 0; i < marketChangeRateDtos.size(); i++) {
//            System.out.println("market: " + marketChangeRateDtos.get(i).getChangeRate());
//        }
        List<Double> marketChangeRates = changDtoToDoubleList(marketChangeRateDtos);

        //입력받은 지표들
        List<Indicator> indicators = indicatorRepository.findByIdIn(backtestIndicatorsDto.getIndicators());
        List<Industry> industries = new ArrayList<>();
        if(backtestIndicatorsDto.getIndustries() !=null){
            industries = backIndustryRepository.findByIdIn(backtestIndicatorsDto.getIndustries());
        }

        List<Industry> industryAllList = backIndustryRepository.findAll();

        for (YearMonth yearMonth : rebalanceYearMonth) {
            // 산업의 배열의 길이가 DB의 산업군 길이 와 같을 때는 위에 코드를 돌리고 아니라면 산업코드 리스트를 추가해서 query문에 쏴서 처리하는 코드 추가!
            List<Trade> trades;
            if (industries.size() != industryAllList.size() && industries.size() != 0) {
                // 산업군 체크 한 회사 목록
                trades = tradeRepository.findByYearAndMonthAndIndustryList(yearMonth.getYear(), yearMonth.getMonth(), industries);

            } else {
                //TODO 입력받은 산업군에 포함된 회사의 Trade만 가져오도록 고칠것
                //이번 분기 매수가 가능한 회사목록
                trades = tradeRepository.findByYearAndMonth(yearMonth.getYear(), yearMonth.getMonth());
            }

//            trades = tradeRepository.findByYearAndMonth(yearMonth.getYear(), yearMonth.getMonth());

            //이번분기 전략에서 구매할 회사
            List<Trade> buyList = calcTradesIndicator(trades, indicators, backtestIndicatorsDto.getMaxStocks());

            //이번분기 전략
            Double revenue = getRevenue(yearMonth, buyList, backtestIndicatorsDto.getRebalance(), history, distHistory);

            changeRateDtos.add(new ChangeRateDto(revenue, yearMonth.getYear(), yearMonth.getMonth()));
            changeRates.add(revenue);
        }

        //누적수익률 (단위 : % )
        List<ChangeRateDto> cumulativeReturn = getCumulativeReturn(changeRateDtos, backtestIndicatorsDto.getFee());
        List<ChangeRateDto> marketCumulativeReturn = getCumulativeReturn(marketChangeRateDtos, backtestIndicatorsDto.getFee());
        result.setCumulativeReturnDtos(CumulativeReturnDto.CumulativeReturnDtos(cumulativeReturn, marketCumulativeReturn));


        //전략 지표들 계산
        result.setChangeRate(changeRateDtos);
        result.setStrategyCumulativeReturn(cumulativeReturn.get(cumulativeReturn.size() - 1).getChangeRate());
        result.setStrategyCagr(getAvg(changeRates));
        result.setStrategySharpe(getSharpe(changeRateDtos, changeRates, backtestIndicatorsDto));
        result.setStrategySortino(getSortino(changeRateDtos, changeRates, backtestIndicatorsDto));
        result.setStrategyRevenue(winCount(changeRates));
        result.setStrategyMDD(getMdd(cumulativeReturn));

        //시장 지표들 계산
        result.setMarketCumulativeReturn(marketCumulativeReturn.get(marketCumulativeReturn.size() - 1).getChangeRate());
        result.setMarketCagr(getAvg(marketChangeRates));
        result.setMargetSharpe(getSharpe(marketChangeRateDtos, marketChangeRates, backtestIndicatorsDto));
        result.setMarketSortino(getSortino(marketChangeRateDtos, marketChangeRates, backtestIndicatorsDto));
        result.setMarketRevenue(winCount(marketChangeRates));
        result.setMarketMDD(getMdd(marketCumulativeReturn));

        result.setTotalMonth(rebalanceYearMonth.size());
        result.setIndicators(indicators.stream().map(Indicator::getId).collect(Collectors.toList()));

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println(history.size());
        for (int i = 0; i < history.size(); i++) {
            for (BuySellDto buySellDto : history.get(i)) {
                System.out.println(buySellDto);
            }
            System.out.println("dist: " + distHistory.get(i));
            System.out.println("===================");
        }

        for (Industry industry : industries) {
            System.out.println(industry.getIndustryName());
        }

        return result;
    }

    //포스피리스트를 리벨런싱 기간메 맞게 ChangeRateDto리스트로 변환
    private List<ChangeRateDto> getKospiList(BacktestIndicatorsDto backtestIndicatorsDto, List<YearMonth> yearMonths) {
        List<ChangeRateDto> result = new ArrayList<>();

        List<Kospi> kospis = kospiRepository.findByStartYearAndEndYear(
                backtestIndicatorsDto.getStartYear(), backtestIndicatorsDto.getEndYear() + 1);
        int startIdx = 0;
        for (; startIdx < kospis.size(); startIdx++) {
            if (kospis.get(startIdx).getYear().equals(backtestIndicatorsDto.getStartYear()) &&
                    kospis.get(startIdx).getMonth().equals(backtestIndicatorsDto.getStartMonth())) {
                break;
            }
        }

        for (YearMonth yearMonth : yearMonths) {
            ChangeRateDto changeRateDto = new ChangeRateDto();
            changeRateDto.setYear(yearMonth.getYear());
            changeRateDto.setMonth(yearMonth.getMonth());
            Double val = 1D;
            for (int i = 0; i < backtestIndicatorsDto.getRebalance(); i++) {
                val = val * ((100 + kospis.get(startIdx).getVolatility()) / 100.0);
                startIdx++;
            }
            changeRateDto.setChangeRate(val);
            result.add(changeRateDto);
        }


        return result;
    }


    //todo fix logic
    private Double getMdd(List<ChangeRateDto> cumulativeReturn) {
        double mdd = 100D;
        double max = 0D;
        double min = 99999999D;
        for (ChangeRateDto changeRateDto : cumulativeReturn) {
            if (max < changeRateDto.getChangeRate()) {
                max = changeRateDto.getChangeRate();
                min = 99999999D;
                continue;
            }

            if (min > changeRateDto.getChangeRate()) {
                min = changeRateDto.getChangeRate();
                double temp = (min - max) / max;
                if (mdd > temp) {
                    mdd = temp;
                }
            }
        }
        if (mdd == 100D) {
            return 0D;
        }
        return mdd * 100D;
    }


    //리벨런스 횟수 계산
    private int getRebalanceCount(int startYear, int startMonth,
                                  int endYear, int endMonth, int rebalance) {
        int result = 0;
        result = endMonth - startMonth + (endYear - startYear) * 12;
        result = result / rebalance;
//        int temp = result % rebalance;
//        if (temp != 0) {
//            result++;
//        }
        return result;
    }


    //샤프지수 계산
    private Double getSharpe(List<ChangeRateDto> changeRateDtos, List<Double> changeRate, BacktestIndicatorsDto backtestIndicatorsDto) {

        Double deviation = getDeviation(changeRate);
        Double result = getRevenueMinusInterest(changeRateDtos, changeRate, backtestIndicatorsDto);
        result = 1.0 * result / deviation;
        return Math.abs(result);
    }

    //소티노 계산
    private Double getSortino(List<ChangeRateDto> changeRateDtos, List<Double> changeRate, BacktestIndicatorsDto backtestIndicatorsDto) {
        Double nagativeDeviation = getNagativeDeviation(changeRate);
        Double result = getRevenueMinusInterest(changeRateDtos, changeRate, backtestIndicatorsDto);
        result = 1.0 * result / nagativeDeviation;
        return Math.abs(result);
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
        avgInterest = 1D + (avgInterest / 100D);
//        System.out.println("avgRevenue: " + avgRevenue + ", avgInterest: " + avgInterest);
        return avgRevenue - avgInterest;
    }

    //기간동안 매수가 생기는 연도,월 반환
    private List<YearMonth> getRebalanceYearMonth(int startYear, int startMonth,
                                                  int endYear, int endMonth, int rebalance) {
        List<YearMonth> result = new ArrayList<>();
        int thisYear = startYear;
        int thisMonth = startMonth;
        result.add(new YearMonth(thisYear, thisMonth));
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
            if (chageRateDto.getChangeRate().equals(0D)) {
                now = now * ((100.0 - fee) / 100);
            } else {
                now = now * chageRateDto.getChangeRate() * ((100.0 - fee) / 100);
            }
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
        return sum / (double) changeRate.size();
    }

    //ChangDto리스트의 변화량을 Double리스트로 변환
    private List<Double> changDtoToDoubleList(List<ChangeRateDto> changeRateDtos) {
        return changeRateDtos.stream().map(changeRateDto -> changeRateDto.getChangeRate()).collect(Collectors.toList());

    }


    //분산계산
    private Double getDeviation(List<Double> list) {
        Double avg = getAvg(list);
        Double sum = 0D;
        for (Double rate : list) {
            Double temp = rate - avg;
            sum += temp * temp;
        }
        sum = 1.0 * sum / (double) list.size();
//        System.out.println("sum: " + sum + ", devi: " + Math.sqrt(sum));

        return Math.sqrt(sum);
    }

    private Double getNagativeDeviation(List<Double> list) {
        Double avg = getAvg(list);
        Double sum = 0D;
        for (Double rate : list) {
            if (rate >= 1) {
                continue;
            }
            Double temp = rate - avg;
            sum += temp * temp;
        }
        sum = 1.0 * sum / (double) list.size();
        System.out.println("sum: " + sum + ", devi: " + Math.sqrt(sum));
        return Math.sqrt(sum);
    }


    //양수 카운트
    private Integer winCount(List<Double> chageRate) {
        int sum = 0;
        for (Double rate : chageRate) {
            if (rate >= 1) {
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
    private Double getRevenue(YearMonth yearMonth, List<Trade> list, int rebalance, List<List<BuySellDto>> history, List<List<Double>> distHistory) {
        Double result = 0D;
        List<Double> dist = new ArrayList<>();
        List<BuySellDto> tradeList = new ArrayList<>();
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
            Trade find = findByCorcode(trade.getCorcode(), byYearAndMonthAndCorcodeList);
            if (find == null) {
                dist.add(1D);
                continue;
            }

//            System.out.println("start: " + trade.getCorname() + ", " + (find.getMarcap().doubleValue() / 100D) + ", " + trade.getYear() + " " + trade.getMonth() + ", end : "
//                    + find.getCorname() + ", " + (trade.getMarcap().doubleValue() / 100D) + ", " + find.getYear() + " " + find.getMonth());

            tradeList.add(new BuySellDto(trade, find));

            double temp = (find.getCorclose().doubleValue()) / (trade.getCorclose().doubleValue());
            if (temp > 2) {
                double check = find.getMarcap().doubleValue() / trade.getMarcap().doubleValue();
                if (Math.abs(temp / check - 1) > 0.3) {
                    continue;
                }
            }
//            double temp = (find.getCorclose().doubleValue() / trade.getCorclose().doubleValue());
            dist.add(temp);
        }

        history.add(tradeList);
        distHistory.add(dist);
//        System.out.println("dist: " + dist);

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
