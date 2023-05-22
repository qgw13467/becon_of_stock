package com.ssafy.beconofstock.strategy.dto;

import com.ssafy.beconofstock.strategy.entity.StrategyDibs;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StrategyDibsDto {

    Long strategyId;
    String userNickname;
    String strategyName;
//    String access;

    public StrategyDibsDto(StrategyDibs strategyDibs) {
        this.strategyId = strategyDibs.getStrategy().getId();
        this.userNickname = strategyDibs.getMember().getNickname();
        this.strategyName = strategyDibs.getStrategy().getTitle();
//        this.access = strategyDibs.getStrategy().getAccessType().name();

    }
}
