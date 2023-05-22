package com.ssafy.beconofstock.strategy.dto;

import com.ssafy.beconofstock.backtest.dto.CumulativeReturnDataDto;
import lombok.Data;

import java.util.List;

@Data
public class StrategyGraphRepresentativeDto {

    private Long strategyId;
    private String title;
    private Boolean representative;

    List<CummulateReturnDto> cummulateReturnDtos;
    CumulativeReturnDataDto cumulativeReturnDataDto;
    List<Long> indicators;
}
