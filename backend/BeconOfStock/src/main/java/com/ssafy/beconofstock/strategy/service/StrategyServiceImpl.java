package com.ssafy.beconofstock.strategy.service;

import com.ssafy.beconofstock.exception.NotFoundException;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.dto.StrategyAddDto;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import com.ssafy.beconofstock.strategy.entity.StrategyIndicator;
import com.ssafy.beconofstock.strategy.repository.IndicatorRepository;
import com.ssafy.beconofstock.strategy.repository.StrategyIndicatorRepository;
import com.ssafy.beconofstock.strategy.repository.StrategyRepository;
import lombok.RequiredArgsConstructor;
import org.jboss.jandex.Index;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StrategyServiceImpl implements StrategyService {

    private final StrategyRepository strategyRepository;
    private final StrategyIndicatorRepository strategyIndicatorRepository;

    private final IndicatorRepository indicatorRepository;

    @Override
    public Map<String, List<Indicator>> getIndicators() {
        Map<String,List<Indicator>> result = new HashMap<>();

        List<Indicator> indicators = indicatorRepository.findAll();

        List<Indicator> price = new ArrayList<>();
        List<Indicator> quality = new ArrayList<>();
        List<Indicator> growth=  new ArrayList<>();

        for(int i=0;i<indicators.size();i++){
            Indicator indicator = indicators.get(i);

            if(indicator.getTitle().startsWith("price")){
                price.add(indicator);
            } else if(indicator.getTitle().startsWith("quality")){
                quality.add(indicator);
            } else if(indicator.getTitle().startsWith("growth")){
                growth.add(indicator);
            }

        }

        result.put("가치 (가격/매출)", price);
        result.put("퀄리티 (가격/자산)",quality);
        result.put("성장성 (이익 성장률)", growth);

        return result;
    }

    @Override
    public List<StrategyIndicator> getStrategy(Long id) {

        Strategy strategy = strategyRepository.findById(id).orElseThrow(() -> new NotFoundException());

        List<StrategyIndicator> strategyIndicatorList = strategyIndicatorRepository.findByStrategy(strategy);

        return strategyIndicatorList;
    }

    @Override
    @Transactional
    public void addStrategy(Member member, StrategyAddDto strategyAddDto) {

        Strategy strategy = new Strategy(member, strategyAddDto);
        Strategy save = strategyRepository.save(strategy);


        List<Indicator> indicators = indicatorRepository.findByIdIn(strategyAddDto.getIndicators());

        for(int i=0;i<indicators.size();i++){
            Indicator indicator = indicators.get(i);
            Long count = indicator.getCount() +1;
            indicator.setCount(count);

            StrategyIndicator strategyIndicator = new StrategyIndicator(strategy, indicators.get(i));
            strategyIndicatorRepository.save(strategyIndicator);
        }

    }


}
