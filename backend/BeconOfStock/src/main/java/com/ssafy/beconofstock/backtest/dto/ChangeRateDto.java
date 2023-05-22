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

    public static ChangeRateDto getStrategyByCumulativeReturnDto(ChangeRateValueDto changeRateValueDto){
        ChangeRateDto changeRateDto = new ChangeRateDto();
        changeRateDto.setChangeRate(changeRateValueDto.getStrategyValue());
        changeRateDto.setYear(changeRateValueDto.getYear());
        changeRateDto.setMonth(changeRateValueDto.getMonth());
        return  changeRateDto;
    }


    public static ChangeRateDto getMarketByCumulativeReturnDto(ChangeRateValueDto changeRateValueDto){
        ChangeRateDto changeRateDto = new ChangeRateDto();
        changeRateDto.setChangeRate(changeRateValueDto.getMarketValue());
        changeRateDto.setYear(changeRateValueDto.getYear());
        changeRateDto.setMonth(changeRateValueDto.getMonth());
        return  changeRateDto;
    }
}
