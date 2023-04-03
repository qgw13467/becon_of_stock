package com.ssafy.beconofstock.spark.service;

import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.backtest.dto.BacktestResultDto;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public interface SparkService {
    BacktestResultDto getBacktestResult(BacktestIndicatorsDto backtestIndicatorsDto);
    Double getRevenueByDataSet(SparkSession spark, Dataset<Row> buy, Dataset<Row> trade, Integer rebalance);
}
