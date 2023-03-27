package com.ssafy.beconofstock.backtest.service;

import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.backtest.dto.BacktestResultDto;
import com.ssafy.beconofstock.backtest.entity.Finance;
import com.ssafy.beconofstock.backtest.entity.Trade;
import com.ssafy.beconofstock.backtest.repository.FinanceRepository;
import com.ssafy.beconofstock.backtest.repository.TradeRepository;
import com.ssafy.beconofstock.exception.NotFoundException;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.repository.IndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BacktestServiceImpl implements BacktestService {

    private final TradeRepository tradeRepository;
    private final FinanceRepository financeRepository;
    private final IndicatorRepository indicatorRepository;

    @Override
    public List<Trade> mappingTradeFinance(Integer year, Integer month) {
        List<Trade> trades = tradeRepository.findByYearAndMonth(year, month);


        List<Integer> months = new ArrayList<>();
        if (1 <= month && month <= 3) {
            months.addAll(List.of(1, 2, 3));
        }
        if (4 <= month && month <= 6) {
            months.addAll(List.of(4, 5, 6));
        }
        if (7 <= month && month <= 9) {
            months.addAll(List.of(7, 8, 9));
        }
        if (10 <= month && month <= 12) {
            months.addAll(List.of(10, 11, 12));
        }

        List<Finance> finances = financeRepository.findByRptYearAndRptMonth(year, months);

        for (Trade trade : trades) {
            Finance finance = findFinanaceByCorName(trade.getCorname(), finances);
            if(finance !=null){
                financeRepository.save(finance);
                trade.setFinance(finance);
                tradeRepository.save(trade);

            }
        }

        return trades;
    }

    @Override
    @Transactional
    public void preprocess(Integer year, Integer month) {

        List<Indicator> indicators = indicatorRepository.findAll();
        for (Indicator indicator : indicators) {
            List<Trade> trades = mappingTradeFinance(year, month);
            if (month == 12) {
                year += 1;
                month = 1;
            }

            List<Trade> nextMonthTrades = mappingTradeFinance(year, month + 1);

            for (Trade trade : trades) {
                if (trade.getFinance() == null) {
                    continue;
                }
                Trade nextTrade = findByCorcode(trade.getCorcode(), nextMonthTrades);
                trade = calcTradeIndicator(trade, nextTrade, indicator);
            }
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


    @Override
    public BacktestResultDto getBacktestResult(BacktestIndicatorsDto backtestIndicatorsDto) {


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

    //리스트의 각 지표 계산 후 정렬
    //TODO
    private List<Trade> calcTradesIndicator(List<Trade> trades, Indicator indicator) {

        return trades;
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
            case "pricePer":
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

            case "growth3MothTake":
                operatingRevenue = trade.getFinance().getOperatingRevenue();
                nextOperatingRevenue = next.getFinance().getOperatingRevenue();
                nextWon = next.getFinance().getWon();
                trade.setGrowth3MonthTake(getBiggerGoodIndicatorMultipleWon(
                        nextOperatingRevenue, nextWon, operatingRevenue, won));
                return trade;
            case "growth12MothTake":
                operatingRevenue = trade.getFinance().getOperatingRevenue();
                nextOperatingRevenue = next.getFinance().getOperatingRevenue();
                nextWon = next.getFinance().getWon();
                trade.setGrowth12MonthTake(getBiggerGoodIndicatorMultipleWon(
                        nextOperatingRevenue, nextWon, operatingRevenue, won));
                return trade;

            case "growth3MothOperatingProfit":
                operatingProfit = trade.getFinance().getOperatingProfit();
                nextOperatingProfit = next.getFinance().getOperatingProfit();
                nextWon = next.getFinance().getWon();
                trade.setGrowth3MonthOperatingProfit(getBiggerGoodIndicatorMultipleWon(
                        nextOperatingProfit, nextWon, operatingProfit, won));
                return trade;
            case "growth12MothOperatingProfit":
                operatingProfit = trade.getFinance().getOperatingProfit();
                nextOperatingProfit = next.getFinance().getOperatingProfit();
                nextWon = next.getFinance().getWon();
                trade.setGrowth12MonthOperatingProfit(getBiggerGoodIndicatorMultipleWon(
                        nextOperatingProfit, nextWon, operatingProfit, won));
                return trade;

            case "growth3MothNetProfit":
                netProfit = trade.getFinance().getNetProfit();
                nextNetProfit = next.getFinance().getNetProfit();
                nextWon = next.getFinance().getWon();
                trade.setGrowth3MonthNetProfit(getBiggerGoodIndicatorMultipleWon(
                        nextNetProfit, nextWon, netProfit, won));
                return trade;
            case "growth12MothNetProfit":
                netProfit = trade.getFinance().getNetProfit();
                nextNetProfit = next.getFinance().getNetProfit();
                nextWon = next.getFinance().getWon();
                trade.setGrowth12MonthNetProfit(getBiggerGoodIndicatorMultipleWon(
                        nextNetProfit, nextWon, netProfit, won));
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
            return 0D;
        } else {
            return 1.0 * (upper * upperMuli) / (lower * lowwerMulit);
        }
    }


}
