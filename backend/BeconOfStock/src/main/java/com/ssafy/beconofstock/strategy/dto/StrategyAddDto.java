package com.ssafy.beconofstock.strategy.dto;

import com.ssafy.beconofstock.strategy.entity.AccessType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StrategyAddDto {

    private String strategyName;
    private List<Long> indicators;
    private AccessType access;
    private Double cumulativeReturn;
    private Double cagr;
    private Double sharpe;




}
