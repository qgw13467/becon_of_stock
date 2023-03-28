package com.ssafy.beconofstock.backtest.service;

import com.ssafy.beconofstock.backtest.entity.Finance;
import com.ssafy.beconofstock.backtest.entity.Trade;
import com.ssafy.beconofstock.backtest.repository.FinanceRepository;
import com.ssafy.beconofstock.backtest.repository.TradeRepository;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.repository.IndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InitService implements ApplicationListener<ContextRefreshedEvent> {

    private final TradeRepository tradeRepository;
    private final FinanceRepository financeRepository;
    private final IndicatorRepository indicatorRepository;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {


        for (Integer year = 2000; year <= 2022; year++) {
//            backtestService.mappingTradeFinance(year);
        }

        for (Integer year = 2000; year <= 2022; year++) {
//            backtestService.preprocess(year);
        }

    }

    private void mappingTradeFinance(Integer year) {

        for (Integer month = 1; month <= 12; month++) {
            Integer userYear = year;
            List<Trade> trades = tradeRepository.findByYearAndMonth(year, month);

            if (1 <= month && month <= 3) {
                userYear -= 1;
            }
            List<Integer> months = getMonths(month);

            List<Finance> finances = financeRepository.findByRptYearAndRptMonth(userYear, months);

            for (Trade trade : trades) {
                Finance finance = findFinanaceByCorName(trade.getCorname(), finances);
                if (finance != null) {
                    financeRepository.save(finance);
                    trade.setFinance(finance);
                    tradeRepository.save(trade);
                }
            }
        }
    }

    private List<Integer> getMonths(Integer month) {
        List<Integer> months = new ArrayList<>();
        if (1 <= month && month <= 3) {
            months.addAll(List.of(10, 11, 12));
        }
        if (4 <= month && month <= 6) {
            months.addAll(List.of(1, 2, 3));
        }
        if (7 <= month && month <= 9) {
            months.addAll(List.of(4, 5, 6));
        }
        if (10 <= month && month <= 12) {
            months.addAll(List.of(7, 8, 9));
        }
        return months;
    }


    private void preprocess(Integer year) {
        //지표 목록 조회
        List<Indicator> indicators = indicatorRepository.findAll();
        Map<Integer, List<Trade>> month12trades = new HashMap<>();

        //1년치 trade, fanance 가져오기
        for (Integer month = 1; month <= 12; month++) {

            List<Trade> temp = tradeRepository.findByYearAndMonthFetch(year, month);
            month12trades.put(month, temp);
        }
        //12월의 다음달
        List<Trade> temp = tradeRepository.findByYearAndMonthFetch(year + 1, 1);
        month12trades.put(13, temp);

        //6개월 전의 trade 가져오기
        for (Integer month = 7; month <= 12; month++) {
            List<Trade> month6Before = tradeRepository.findByYearAndMonthFetch(year - 1, month);
            month12trades.put(month - 12, month6Before);
        }

        //1년간
        for (Integer month = 1; month <= 12; month++) {

            for (Trade trade : month12trades.get(month)) {

                //다음달 거래를 가져와서
                List<Trade> nextMonthTrades = month12trades.get(month + 1);
                //각 거래에 대해
                if (trade.getFinance() == null) {
                    continue;
                }
                //다음달 같은 회사의 거래를 찾아
                Trade nextTrade = findByCorcode(trade.getCorcode(), nextMonthTrades);

                List<Trade> befor3MonthTrades = month12trades.get(month - 3);
                Trade next3MonthTrade = findByCorcode(trade.getCorcode(), befor3MonthTrades);

                List<Trade> befor6MonthTrades = month12trades.get(month - 6);
                Trade before6MonthTrade = findByCorcode(trade.getCorcode(), befor6MonthTrades);

                for (Indicator indicator : indicators) {
                    int indicatorType = getGrowthByTitle(indicator.getTitle());
                    //growth계열의 지표이면
                    if (indicatorType != 0) {
                        //단위가 3개월이면,
                        if (indicatorType == 1) {

                            if (next3MonthTrade == null) {
                                continue;
                            }

                            //각 거래에서 재무가 not null을 확인하고
                            if (trade.getFinance() == null || next3MonthTrade.getFinance() == null) {
                                continue;
                            }
                            //3개월후와 비교후 저장
                            trade = calcGrowth(trade, next3MonthTrade.getFinance(), indicator);


                        } else {
                            //단위가 6개월이면

                            //6개월 후의 거래가 널인지 확인하고
                            if (before6MonthTrade == null) {
                                continue;
                            }

                            //각 거래에서 재무가 not null을 확인하고
                            if (trade.getFinance() == null || before6MonthTrade.getFinance() == null) {
                                continue;
                            }
                            //6개월전과 비교후 저장
                            trade = calcGrowth(trade, before6MonthTrade.getFinance(), indicator);

                        }
                        continue;

                    }

//                    지표를 계산해서 trade 변경
                    trade = calcTradeIndicator(trade, nextTrade, indicator);

                }
                //trade의 변경이 끝나뭔 쿼리 보냄
                tradeRepository.save(trade);

            }
        }
    }

    private Integer getGrowthByTitle(String title) {

        switch (title) {
            case "growth3MonthTake":
            case "growth3MonthOperatingProfit":
            case "growth3MonthNetProfit":
                return 1;
            case "growth6MonthOperatingProfit":
            case "growth6MonthTake":
            case "growth6MonthNetProfit":
                return 2;
            default:
                return 0;
        }
    }

    private Finance findFinanaceByCorName(String corName, List<Finance> finances) {
        Finance result = null;
        for (Finance finance : finances) {
            if (finance.getCorName().equals(corName)) {
                result = finance;
                break;
            }
        }
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


    //특정기간 trade, finance 리벨런스기간 trade 가져오기
//    private List<Trade> getTradeByStartTime(Integer year, Integer month, Integer rebalance) {
//        List<Trade> startByYearAndMonth = tradeRepository.findByYearAndMonthFetch(year, month);
//        List<Trade> endByYearAndMonth = new ArrayList<>();
//        if (month + rebalance > 12) {
//            year++;
//            month = (month + rebalance) % 12;
//            endByYearAndMonth = tradeRepository.findByYearAndMonth(year, month);
//        }
//        List<Trade> result = new ArrayList<>();
//        result.addAll(startByYearAndMonth);
//        result.addAll(endByYearAndMonth);
//        return result;
//    }

    //각 지표별 값 계산
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

}