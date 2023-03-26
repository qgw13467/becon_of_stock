package com.ssafy.beconofstock.backtest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BacktestResultDto {

    List<BacktestPriceDto> strategyValues;
    List<BacktestPriceDto> marketValues;
    private Double cumulativeReturn;
    private Double cagr;
    private Double sharpe;


}

