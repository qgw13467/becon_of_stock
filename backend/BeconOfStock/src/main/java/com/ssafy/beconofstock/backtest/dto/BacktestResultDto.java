package com.ssafy.beconofstock.backtest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BacktestResultDto {

    List<BacktestPrice> strategyValues;
    List<BacktestPrice> marketValues;
    private Double cumulativeReturn;
    private Double cagr;
    private Double sharpe;


}

