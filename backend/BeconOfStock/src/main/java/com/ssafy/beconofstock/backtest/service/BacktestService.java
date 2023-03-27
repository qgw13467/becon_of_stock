package com.ssafy.beconofstock.backtest.service;

import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.backtest.dto.BacktestResultDto;
import com.ssafy.beconofstock.backtest.entity.Trade;

import java.util.List;

public interface BacktestService {
    List<Trade> mappingTradeFinance(Integer year, Integer month);
    void preprocess(Integer year, Integer month);
    BacktestResultDto getBacktestResult(BacktestIndicatorsDto backtestIndicatorsDto);

}
