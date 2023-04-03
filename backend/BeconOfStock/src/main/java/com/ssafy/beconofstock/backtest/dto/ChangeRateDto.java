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

    public static ChangeRateDto getStrategyByCumulativeReturnDto(CumulativeReturnDto cumulativeReturnDto){
        ChangeRateDto changeRateDto = new ChangeRateDto();
        changeRateDto.setChangeRate(cumulativeReturnDto.getStrategyValue());
        changeRateDto.setYear(cumulativeReturnDto.getYear());
        changeRateDto.setMonth(cumulativeReturnDto.getMonth());
        return  changeRateDto;
    }


    public static ChangeRateDto getMarketByCumulativeReturnDto(CumulativeReturnDto cumulativeReturnDto){
        ChangeRateDto changeRateDto = new ChangeRateDto();
        changeRateDto.setChangeRate(cumulativeReturnDto.getMarketValue());
        changeRateDto.setYear(cumulativeReturnDto.getYear());
        changeRateDto.setMonth(cumulativeReturnDto.getMonth());
        return  changeRateDto;
    }
}
