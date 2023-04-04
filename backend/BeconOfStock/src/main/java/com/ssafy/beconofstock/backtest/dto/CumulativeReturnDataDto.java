package com.ssafy.beconofstock.backtest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CumulativeReturnDataDto {

    private final String culmulativeReturn = "누적 수익률";
    private final String cumulativeReturnDesc = "전략의 누적 수익률";
    private Double strategyCumulativeReturn;
    private Double marketCumulativeReturn;

    //기간별 평균 수익률
    private final String cagr ="구간별 평균 수익률";
    private final  String cagrDesc = "리벨런싱 구간 평균 수익률";
    private Double strategyCagr;
    private Double marketCagr;

    private final String sharpe = "sharpe";
    private final  String sharpeDesc = "변동폭을 확인하는 지표";
    private Double strategySharpe;
    private Double marketSharpe;
    private final String sortino = "sortino";
    private final String sortinoDesc = "하락에 대한 변동폭을 확인하는 지표";
    private Double strategySortino;
    private Double marketSortino;

    //상관계수
//    private Double correlation;

    //최대 하락폭

    private final String mdd = "MDD";
    private final String mddDesc = "최대 하락폭을 확인하는 지표";
    private Double strategyMDD;
    private Double marketMDD;

}
