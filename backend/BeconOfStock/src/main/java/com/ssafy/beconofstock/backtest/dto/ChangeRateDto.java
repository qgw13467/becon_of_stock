package com.ssafy.beconofstock.backtest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRateDto {
    private Double changeRate;
    private Integer year;
    private Integer month;

}
