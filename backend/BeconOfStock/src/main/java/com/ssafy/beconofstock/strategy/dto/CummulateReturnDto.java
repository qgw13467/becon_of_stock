package com.ssafy.beconofstock.strategy.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CummulateReturnDto {

    private Integer year;
    private Integer month;
    private Double strategyValue;
    private Double marketValue;
}
