package com.ssafy.beconofstock.backtest.service;

import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.backtest.dto.BacktestResultDto;

import java.util.List;

public interface BacktestService {

    BacktestResultDto getBacktestResult(BacktestIndicatorsDto backtestIndicatorsDto);

}
