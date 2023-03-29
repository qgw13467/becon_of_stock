package com.ssafy.beconofstock.strategy.dto;

import com.ssafy.beconofstock.strategy.entity.Strategy;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StrategyListDto {
    Long strategyId;
    String title;
    Double cumulativeReturn;
    Double cagr;
    Double sharpe;

    public StrategyListDto(Strategy strategy) {
        this.strategyId = strategy.getId();
        this.title = strategy.getTitle();
        this.cumulativeReturn = strategy.getCumulativeReturn();
        this.cagr = strategy.getCagr();
        this.sharpe = strategy.getSharpe();
    }
}
