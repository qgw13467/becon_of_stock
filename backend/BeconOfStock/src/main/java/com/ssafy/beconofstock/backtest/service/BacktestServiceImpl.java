package com.ssafy.beconofstock.backtest.service;


import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.backtest.dto.BacktestResultDto;
import com.ssafy.beconofstock.backtest.entity.Finance;
import com.ssafy.beconofstock.backtest.entity.Trade;
import com.ssafy.beconofstock.backtest.repository.FinanceRepository;
import com.ssafy.beconofstock.backtest.repository.TradeRepository;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.repository.IndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class BacktestServiceImpl implements BacktestService {

    private final TradeRepository tradeRepository;
    private final FinanceRepository financeRepository;
    private final IndicatorRepository indicatorRepository;


    @Override
    public BacktestResultDto getBacktestResult(BacktestIndicatorsDto backtestIndicatorsDto) {

        Double strategyCumulativeReturn, strategyCagr, strategySharpe, strategySortino, correlation,
                strategyMDD, strategyRevenue, strategyRevenueAvg;

        List<Double> changeRate = new ArrayList<>();

        return null;
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

        return trades;
    }

    // TODO : 모든 지표에 대한 ranking 평균 계산
    private void calAverageRanking(List<Trade> trades) {
        for (Trade trade : trades) {
            trade.setRanking(trade.getRanking() / trade.getCnt());
        }
    }

    // TODO : 새로운 지표 ranking 갱신
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

    // TODO : 지표별 Comparator 리턴
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
}
