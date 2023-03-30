package com.ssafy.beconofstock.strategy.dto;

import com.ssafy.beconofstock.backtest.dto.ChangeRateDto;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StrategyListDto {
    Long strategyId;
    String title;
    List<ChangeRateDto> marketValues;
    List<ChangeRateDto> strategyValues;
    List<Indicator> indicators;

    public StrategyListDto(Strategy strategy, List<ChangeRateDto> strategyValues, List<ChangeRateDto> marketValues) {
        this.strategyId = strategy.getId();
        this.title = strategy.getTitle();
        this.strategyValues = strategyValues;
        this.marketValues = marketValues;
    }

}
