package com.ssafy.beconofstock.backtest.service;

import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.backtest.dto.BacktestResultDto;
import com.ssafy.beconofstock.backtest.entity.Trade;
import com.ssafy.beconofstock.backtest.repository.FinanceRepository;
import com.ssafy.beconofstock.backtest.repository.TrateRepository;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.repository.IndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BacktestServiceImpl implements BacktestService {

    private final TrateRepository trateRepository;
    private final FinanceRepository financeRepository;
    private final IndicatorRepository indicatorRepository;

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
        List<Trade> startByYearAndMonth = trateRepository.findByYearAndMonthFetch(year, month);
        List<Trade> endByYearAndMonth = new ArrayList<>();
        if (month + rebalance > 12) {
            year++;
            month = (month + rebalance) % 12;
            endByYearAndMonth = trateRepository.findByYearAndMonth(year, month);
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
                trade.setIndicatorValue(getLowerGoodIndicatorMultipleWon(marcap, 1, netProfit, won));
                return trade;
            case "pricePBR":
                totalAssets = trade.getFinance().getTotalAssets();
                trade.setIndicatorValue(getLowerGoodIndicatorMultipleWon(marcap, 1, totalAssets, won));
                return trade;
            case "pricePSR":
                operatingRevenue = trade.getFinance().getOperatingRevenue();
                trade.setIndicatorValue(getLowerGoodIndicatorMultipleWon(marcap, 1, operatingRevenue, won));
                return trade;
            case "pricePOR":
                operatingProfit = trade.getFinance().getOperatingProfit();
                trade.setIndicatorValue(getLowerGoodIndicatorMultipleWon(marcap, 1, operatingProfit, won));
                return trade;
            case "qualityROE":
                totalAssets = trade.getFinance().getTotalAssets();
                trade.setIndicatorValue(getBiggerGoodIndicatorMultipleWon(netProfit, won, totalAssets, won));
                return trade;
            case "qualityROA":
                totalCapital = trade.getFinance().getTotalCapital();
                trade.setIndicatorValue(getBiggerGoodIndicatorMultipleWon(netProfit, won, totalCapital, won));
                return trade;

            case "growth3MothTake":
            case "growth12MothTake":
                operatingRevenue = trade.getFinance().getOperatingRevenue();
                nextOperatingRevenue = next.getFinance().getOperatingRevenue();
                nextWon = next.getFinance().getWon();
                trade.setIndicatorValue(getBiggerGoodIndicatorMultipleWon(
                        nextOperatingRevenue, nextWon, operatingRevenue, won));
                return trade;

            case "growth3MothOperatingProfit":
            case "growth12MothOperatingProfit":
                operatingProfit = trade.getFinance().getOperatingProfit();
                nextOperatingProfit = next.getFinance().getOperatingProfit();
                nextWon = next.getFinance().getWon();
                trade.setIndicatorValue(getBiggerGoodIndicatorMultipleWon(
                        nextOperatingProfit, nextWon, operatingProfit, won));
                return trade;

            case "growth3MothNetProfit":
            case "growth12MothNetProfit":
                netProfit = trade.getFinance().getNetProfit();
                nextNetProfit = next.getFinance().getNetProfit();
                nextWon = next.getFinance().getWon();
                trade.setIndicatorValue(getBiggerGoodIndicatorMultipleWon(
                        nextNetProfit, nextWon, netProfit, won));
                return trade;

            default:
                trade.setIndicatorValue(-1D);
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
