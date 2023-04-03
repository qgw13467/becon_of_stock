package com.ssafy.beconofstock.spark.service;

import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.spark.dto.TestDto;

public interface SparkService {
    TestDto getConnectionTest(BacktestIndicatorsDto backtestIndicatorsDto);

}
