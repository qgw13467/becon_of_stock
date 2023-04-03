package com.ssafy.beconofstock.spark.service;

import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.backtest.dto.BacktestResultDto;

public interface SparkService {
    BacktestResultDto getBacktestResult(BacktestIndicatorsDto backtestIndicatorsDto);

}
