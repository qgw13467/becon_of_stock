package com.ssafy.beconofstock.backtest.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BacktestPriceDto {

    private Integer year;
    private Integer month;
    private Double value;
}
