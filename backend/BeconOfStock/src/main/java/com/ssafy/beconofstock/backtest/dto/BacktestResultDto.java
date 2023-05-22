package com.ssafy.beconofstock.backtest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BacktestResultDto {
    //누적 수익률
    List<ChangeRateValueDto> cumulativeReturnDtos;

    CumulativeReturnDataDto cumulativeReturnDataDto;

    //각 기간(연도, 달)별 수익률
    List<ChangeRateValueDto> changeRate;

    private RevenueDataDto revenueDataDto;

    List<Long> indicators;

    Integer rebalance;

    //주기별 수익률 평균
//    private Double strategyRevenueAvg;
//    private Double marketRevenueAvg;


}

