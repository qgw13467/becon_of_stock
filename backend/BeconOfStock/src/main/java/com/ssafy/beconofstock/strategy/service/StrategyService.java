package com.ssafy.beconofstock.strategy.service;

import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.dto.IndicatorsDto;
import com.ssafy.beconofstock.strategy.dto.IndustriesDto;
import com.ssafy.beconofstock.strategy.dto.StrategyAddDto;
import com.ssafy.beconofstock.strategy.dto.StrategyDetailDto;
import com.ssafy.beconofstock.strategy.entity.StrategyIndicator;

import java.util.List;

public interface StrategyService {


    StrategyDetailDto getStrategyDetail(Member member, Long strategyId);
    IndicatorsDto getIndicators();
    IndustriesDto getIndustries();
    List<StrategyIndicator> getStrategy(Long id);
    void addStrategy(Member member, StrategyAddDto strategyAddDto);
    void patchStrategy(Member member, StrategyAddDto strategyAddDto, Long strategyId);

    void deleteStrategy(Member member, Long StrategyId);
}
