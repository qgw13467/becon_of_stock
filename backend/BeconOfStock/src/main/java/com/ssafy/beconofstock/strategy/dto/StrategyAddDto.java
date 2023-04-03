package com.ssafy.beconofstock.strategy.dto;

import com.ssafy.beconofstock.backtest.dto.ChangeRateDto;
import com.ssafy.beconofstock.backtest.dto.CumulativeReturnDto;
import com.ssafy.beconofstock.strategy.entity.AccessType;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StrategyAddDto {

    // 전략 이름
    private String title;
    //누적 수익률

    List<CumulativeReturnDto> cumulativeReturnDtos;

    // 선택한 지표 저장
    List<Long> indicators;

    private Double strategyCumulativeReturn;
    private Double strategyCagr;
    private Double strategySharpe;
    private Double strategySortino;
    private Double strategyMDD;
    //

    private Integer strategyRevenue;
    private Integer totalMonth;

//    private AccessType access;


}
