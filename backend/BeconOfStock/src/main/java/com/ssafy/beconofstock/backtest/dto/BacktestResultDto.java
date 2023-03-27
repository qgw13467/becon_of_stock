package com.ssafy.beconofstock.backtest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BacktestResultDto {

    List<BacktestPriceDto> strategyValues;
    List<BacktestPriceDto> marketValues;
    private Double strategyCumulativeReturn;
    private Double marketCumulativeReturn;
    private Double strategyCagr;
    private Double marketCagr;
    private Double strategySharpe;
    private Double margetSharpe;
    private Double strategySortino;
    private Double marketSortino;
    private Double correlation;
    private Double strategyMDD;
    private Double marketMDD;
    List<ChangeRateDto> changeRate;
    private Integer strategyRevenue;
    private Integer marketRevenue;
    private Integer totalMonth;
    private Double strategyRevenueAvg;
    private Double marketRevenueAvg;


}

