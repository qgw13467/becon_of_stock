package com.ssafy.beconofstock.backtest.service;


import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.backtest.dto.BacktestResultDto;
import com.ssafy.beconofstock.backtest.dto.YearMonth;
import com.ssafy.beconofstock.backtest.entity.InterestRate;
import com.ssafy.beconofstock.backtest.repository.FinanceRepository;
import com.ssafy.beconofstock.backtest.repository.InterestRateRepository;
import com.ssafy.beconofstock.backtest.repository.TradeRepository;
import com.ssafy.beconofstock.strategy.repository.IndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BacktestServiceImpl implements BacktestService {

    private final TradeRepository tradeRepository;
    private final FinanceRepository financeRepository;
    private final IndicatorRepository indicatorRepository;
    private final InterestRateRepository interestRateRepository;

    @Override
    public BacktestResultDto getBacktestResult(BacktestIndicatorsDto backtestIndicatorsDto) {

        Double strategyCumulativeReturn, strategyCagr, strategySharpe, strategySortino, correlation,
                strategyMDD, strategyRevenue, strategyRevenueAvg;

        List<Double> changeRate = new ArrayList<>();

        return null;
    }

    //리벨런스 횟수 계산
    private Integer getRebalanceCount(BacktestIndicatorsDto backtestIndicatorsDto) {
        Integer startYear = backtestIndicatorsDto.getStartYear();
        Integer startMonth = backtestIndicatorsDto.getStartMonth();
        Integer endYear = backtestIndicatorsDto.getEndYear();
        Integer endMonth = backtestIndicatorsDto.getEndMonth();
        Integer rebalance = backtestIndicatorsDto.getRebalance();

        return getRebalanceCount(startYear, startMonth, endYear, endMonth, rebalance);
    }

    //리벨런스 횟수 계산
    private Integer getRebalanceCount(Integer startYear, Integer startMonth,
                                      Integer endYear, Integer endMonth, Integer rebalance) {
        Integer result = 0;
        result = endMonth - startMonth + (endYear - startYear) * 12;
        result = result / rebalance;
        return result;
    }


    //샤프지수 계산
    private Double calcSharpe(List<Double> changeRate, BacktestIndicatorsDto backtestIndicatorsDto) {

        Double deviation = getDeviation(changeRate);
        Double result = getRevenueMinusInterest(changeRate, backtestIndicatorsDto);
        result = 1.0 * result / deviation;
        return result;
    }

    //소티노 계산
    private Double calcSortino(List<Double> changeRate, BacktestIndicatorsDto backtestIndicatorsDto){
        Double nagativeDeviation = getNagativeDeviation(changeRate);
        Double result = getRevenueMinusInterest(changeRate, backtestIndicatorsDto);
        result = 1.0 * result / nagativeDeviation;
        return result;
    }

    //샤프,소티노 공통부분 분리
    private  Double getRevenueMinusInterest(List<Double> changeRate, BacktestIndicatorsDto backtestIndicatorsDto){
        List<YearMonth> yearMonths = getRebalanceYearMonth(backtestIndicatorsDto);
        List<InterestRate> interestRates =
                interestRateRepository.findByYearMonthList(
                        backtestIndicatorsDto.getStartYear(),
                        backtestIndicatorsDto.getEndYear());

        Double avgRevenue = getAvg(changeRate);

        List<Double> interests = new ArrayList<>();
        for (int i = 0; i < changeRate.size(); i++) {
            YearMonth thisYearMonth = yearMonths.get(0);
            InterestRate interestRate =
                    getInterestRateByYearMonth(interestRates, thisYearMonth.getYear(), thisYearMonth.getMonth());
            interests.add(interestRate.getInterestRate());
        }
        Double avgInterest = getAvg(interests);

        return avgRevenue - avgInterest;
    }

    //
    private List<YearMonth> getRebalanceYearMonth(Integer startYear, Integer startMonth,
                                                  Integer endYear, Integer endMonth, Integer rebalance) {
        List<YearMonth> result = new ArrayList<>();
        Integer thisYear = startYear;
        Integer thisMonth = startMonth;
        for (Integer start = 0; start < getRebalanceCount(startYear, startMonth, endYear, endMonth, rebalance); start++) {
            thisMonth += start * rebalance;
            if (thisMonth > 12) {
                thisYear++;
                thisMonth = thisMonth - 12;
            }
            result.add(new YearMonth(thisYear, thisMonth));
        }
        return result;
    }

    private List<YearMonth> getRebalanceYearMonth(BacktestIndicatorsDto backtestIndicatorsDto) {
        Integer startYear = backtestIndicatorsDto.getStartYear();
        Integer startMonth = backtestIndicatorsDto.getStartMonth();
        Integer endYear = backtestIndicatorsDto.getEndYear();
        Integer endMonth = backtestIndicatorsDto.getEndMonth();
        Integer rebalance = backtestIndicatorsDto.getRebalance();

        return getRebalanceYearMonth(startYear, startMonth, endYear, endMonth, rebalance);
    }

    //누적 수익률 계산
    private Double calcCumulativeReturn(List<Double> changeRate, Double fee) {
        Double result = 1D;
        for (Double rate : changeRate) {
            result = result * rate * fee;
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

    private Double getNagativeDeviation(List<Double> list){
        Double avg = getAvg(list);
        Double sum = 0D;
        for (Double rate : list) {
            if(rate>=0){
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
            if (interestRate.getYear() == year && interestRate.getMonth() == month) {
                result = interestRate;
                break;
            }
        }
        return result;
    }

}
