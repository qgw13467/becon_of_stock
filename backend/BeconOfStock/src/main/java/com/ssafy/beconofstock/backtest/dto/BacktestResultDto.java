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
    private String cumulativeReturnDesc = "전략의 누적 수익률";
    private Double strategyCumulativeReturn;
    private Double marketCumulativeReturn;

    //기간별 평균 수익률
    private String cagrDesc = "리벨런싱 구간 평균 수익률";
    private Double strategyCagr;
    private Double marketCagr;

    private String sharpe = "변동폭을 확인하는 지표";
    private Double strategySharpe;
    private Double margetSharpe;
    private String sortino = "하락에 대한 변동폭을 확인하는 지표";
    private Double strategySortino;
    private Double marketSortino;

    //상관계수
//    private Double correlation;

    //최대 하락폭
    private String mdd = "최대 하락폭을 확인하는 지표";
    private Double strategyMDD;
    private Double marketMDD;

    //각 기간(연도, 달)별 수익률
    private String changeRateDesc ="구간별 변화량";
    List<ChangeRateDto> changeRate;

    //수익을 만든 횟수
    private String winrate = "수익을 만든 구간의 횟수";
    private Integer strategyRevenue;
    private Integer marketRevenue;
    private Integer totalMonth;

    //주기별 수익률 평균
//    private Double strategyRevenueAvg;
//    private Double marketRevenueAvg;


}

