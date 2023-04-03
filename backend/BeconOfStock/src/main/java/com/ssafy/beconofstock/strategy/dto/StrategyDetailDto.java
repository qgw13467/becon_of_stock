package com.ssafy.beconofstock.strategy.dto;

import com.ssafy.beconofstock.backtest.dto.ChangeRateDto;
import com.ssafy.beconofstock.strategy.entity.AccessType;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import io.swagger.models.auth.In;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class StrategyDetailDto {

    private Long id;
    private Long memberId;
    private String memberNickname;
    private List<Indicator>  indicators = new ArrayList<>();
    private String title;
    private Boolean representative;

    List<ChangeRateDto> marketValues;
    List<ChangeRateDto> strategyValues;
//    private Double cumulativeReturn;
//    private Double cagr;
//    private Double sharpe;

//    private AccessType access;


}
