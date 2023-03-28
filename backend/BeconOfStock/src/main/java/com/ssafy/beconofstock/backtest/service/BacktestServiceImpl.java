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

}
