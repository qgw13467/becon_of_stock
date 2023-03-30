package com.ssafy.beconofstock.backtest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BacktestResultDto {

    List<ChangeRateDto> strategyValues;
    List<ChangeRateDto> marketValues;
    //누적 수익률
    private Double strategyCumulativeReturn;
    private Double marketCumulativeReturn;

    //기간별 평균 수익률
    private Double strategyCagr;
    private Double marketCagr;

    private Double strategySharpe;
    private Double margetSharpe;
    private Double strategySortino;
    private Double marketSortino;

    //상관계수
//    private Double correlation;

    //최대 하락폭
    private Double strategyMDD;
    private Double marketMDD;

    //각 기간(연도, 달)별 수익률
    List<ChangeRateDto> changeRate;

    //수익을 만든 횟수
    private Integer strategyRevenue;
    private Integer marketRevenue;
    private Integer totalMonth;

    //주기별 수익률 평균
//    private Double strategyRevenueAvg;
//    private Double marketRevenueAvg;


}

