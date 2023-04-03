package com.ssafy.beconofstock.backtest.dto;

import lombok.*;
import org.apache.hadoop.thirdparty.org.checkerframework.checker.builder.qual.NotCalledMethods;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueDataDto {

    //수익을 만든 횟수
    private final String winrate = "수익을 만든 구간의 횟수";
    private Integer strategyRevenue;
    private Integer marketRevenue;
    private Integer totalMonth;
}
