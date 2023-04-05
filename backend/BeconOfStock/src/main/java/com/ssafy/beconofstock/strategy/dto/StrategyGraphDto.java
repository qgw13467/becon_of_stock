package com.ssafy.beconofstock.strategy.dto;

import com.ssafy.beconofstock.backtest.dto.ChangeRateValueDto;
import com.ssafy.beconofstock.backtest.dto.CumulativeReturnDataDto;
import com.ssafy.beconofstock.backtest.entity.CummulateReturn;
import lombok.Data;

import java.util.List;

@Data
public class StrategyGraphDto {

    private Long strategyId;
    private String title;

    List<CummulateReturnDto> cummulateReturnDtos;
    CumulativeReturnDataDto cumulativeReturnDataDto;
    List<Long> indicators;
}
