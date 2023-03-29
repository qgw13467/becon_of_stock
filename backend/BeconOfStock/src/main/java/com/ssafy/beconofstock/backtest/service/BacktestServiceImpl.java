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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    //특정기간 trade, finance 리벨런스기간 trade 가져오기
    private List<Trade> getTradeByStartTime(Integer year, Integer month, Integer rebalance) {
        List<Trade> startByYearAndMonth = tradeRepository.findByYearAndMonthFetch(year, month);
        List<Trade> endByYearAndMonth = new ArrayList<>();
        if (month + rebalance > 12) {
            year++;
            month = (month + rebalance) % 12;
            endByYearAndMonth = tradeRepository.findByYearAndMonth(year, month);
        }
        List<Trade> result = new ArrayList<>();
        result.addAll(startByYearAndMonth);
        result.addAll(endByYearAndMonth);
        return result;
    }

    //각 지표별 값 계산
    //TODO
    private Trade calcTradeIndicator(Trade trade, Trade next, Indicator indicator) {
        Long marcap = trade.getMarcap();
        Integer won = trade.getFinance().getWon();
        Long netProfit = trade.getFinance().getNetProfit();

        Long totalAssets, operatingRevenue, operatingProfit, totalCapital;
        Long nextNetProfit, nextOperatingRevenue, nextOperatingProfit;
        Integer nextWon;

        switch (indicator.getTitle()) {
            case "pricePER":
                trade.setPricePER(getLowerGoodIndicatorMultipleWon(marcap, 1, netProfit, won));
                return trade;
            case "pricePBR":
                totalAssets = trade.getFinance().getTotalAssets();
                trade.setPricePBR(getLowerGoodIndicatorMultipleWon(marcap, 1, totalAssets, won));
                return trade;
            case "pricePSR":
                operatingRevenue = trade.getFinance().getOperatingRevenue();
                trade.setPricePSR(getLowerGoodIndicatorMultipleWon(marcap, 1, operatingRevenue, won));
                return trade;
            case "pricePOR":
                operatingProfit = trade.getFinance().getOperatingProfit();
                trade.setPricePOR(getLowerGoodIndicatorMultipleWon(marcap, 1, operatingProfit, won));
                return trade;
            case "qualityROE":
                totalAssets = trade.getFinance().getTotalAssets();
                trade.setQualityROE(getBiggerGoodIndicatorMultipleWon(netProfit, won, totalAssets, won));
                return trade;
            case "qualityROA":
                totalCapital = trade.getFinance().getTotalCapital();
                trade.setQualityROA(getBiggerGoodIndicatorMultipleWon(netProfit, won, totalCapital, won));
                return trade;


        }
        return trade;
    }

    private Trade calcGrowth(Trade trade, Finance finance, Indicator indicator) {
        Long marcap = trade.getMarcap();
        Integer won = trade.getFinance().getWon();

        Long totalAssets, operatingRevenue, operatingProfit, totalCapital;
        Long beforeNetProfit, beforeOperatingRevenue, beforeOperatingProfit, netProfit;
        Integer beforeWon = finance.getWon();
        switch (indicator.getTitle()) {
            case "growth3MonthTake":
                operatingRevenue = trade.getFinance().getOperatingRevenue();
                beforeOperatingRevenue = finance.getOperatingRevenue();
                trade.setGrowth3MonthTake(getBiggerGoodIndicatorMultipleWon(operatingRevenue, won, beforeOperatingRevenue, beforeWon));
                return trade;
            case "growth6MonthTake":
                operatingRevenue = trade.getFinance().getOperatingRevenue();
                beforeOperatingRevenue = finance.getOperatingRevenue();
                trade.setGrowth6MonthTake(getBiggerGoodIndicatorMultipleWon(operatingRevenue, won, beforeOperatingRevenue, beforeWon));
                return trade;

            case "growth3MonthOperatingProfit":
                operatingProfit = trade.getFinance().getOperatingProfit();
                beforeOperatingProfit = finance.getOperatingProfit();
                trade.setGrowth3MonthOperatingProfit(getBiggerGoodIndicatorMultipleWon(operatingProfit, won, beforeOperatingProfit, beforeWon));
                return trade;
            case "growth6MonthOperatingProfit":
                operatingProfit = trade.getFinance().getOperatingProfit();
                beforeOperatingProfit = finance.getOperatingProfit();
                trade.setGrowth6MonthOperatingProfit(getBiggerGoodIndicatorMultipleWon(operatingProfit, won, beforeOperatingProfit, beforeWon));
                return trade;

            case "growth3MonthNetProfit":
                netProfit = trade.getFinance().getNetProfit();
                beforeNetProfit = finance.getNetProfit();
                trade.setGrowth3MonthNetProfit(getBiggerGoodIndicatorMultipleWon(netProfit, won, beforeNetProfit, beforeWon));
                return trade;
            case "growth6MonthNetProfit":
                netProfit = trade.getFinance().getNetProfit();
                beforeNetProfit = finance.getNetProfit();
                trade.setGrowth6MonthNetProfit(getBiggerGoodIndicatorMultipleWon(netProfit, won, beforeNetProfit, beforeWon));
                return trade;


        }
        return trade;
    }

    //낮은게 좋은 지표에서 쓰이는 단위계산
    private Double getLowerGoodIndicatorMultipleWon(Long upper, Integer upperMuli, Long lower, Integer lowwerMulit) {
        if (lower == null || lower == 0 || upperMuli == 0 || lowwerMulit == 0) {
            return 99999D;
        } else {
            return 1.0 * (upper * upperMuli) / (lower * lowwerMulit);
        }
    }

    //높은게 좋은 지표에서 쓰이는 단위계산
    private Double getBiggerGoodIndicatorMultipleWon(Long upper, Integer upperMuli, Long lower, Integer lowwerMulit) {
        if (lower == null || lower == 0 || upperMuli == 0 || lowwerMulit == 0) {
            return -99D;
        } else {
            return 1.0 * (upper * upperMuli) / (lower * lowwerMulit);
        }
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
