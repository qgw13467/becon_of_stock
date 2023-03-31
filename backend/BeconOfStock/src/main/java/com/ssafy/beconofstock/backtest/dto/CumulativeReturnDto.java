package com.ssafy.beconofstock.backtest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class CumulativeReturnDto {

    private Integer year;
    private Integer month;
    private Double strategyValue;
    private Double marketValue;


    public static List<CumulativeReturnDto> CumulativeReturnDtos(List<ChangeRateDto> stratgy, List<ChangeRateDto> market) {
        List<CumulativeReturnDto> result = new ArrayList<>();
        for (int i = 0; i < stratgy.size(); i++) {
            result.add(
                    CumulativeReturnDto.builder()
                            .year(stratgy.get(i).getYear())
                            .month(stratgy.get(i).getMonth())
                            .strategyValue(stratgy.get(i).getChangeRate())
                            .marketValue(market.get(i).getChangeRate())
                            .build()
            );
        }

        return result;
    }
}
