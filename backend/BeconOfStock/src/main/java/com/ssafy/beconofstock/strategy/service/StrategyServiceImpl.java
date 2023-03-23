package com.ssafy.beconofstock.strategy.service;

import com.ssafy.beconofstock.exception.NotFoundException;
import com.ssafy.beconofstock.exception.NotYourAuthorizationException;
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
import org.yaml.snakeyaml.reader.StreamReader;

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
        Map<String, List<Indicator>> result = new HashMap<>();

        List<Indicator> indicators = indicatorRepository.findAll();

        List<Indicator> price = new ArrayList<>();
        List<Indicator> quality = new ArrayList<>();
        List<Indicator> growth = new ArrayList<>();

        for (int i = 0; i < indicators.size(); i++) {
            Indicator indicator = indicators.get(i);

            if (indicator.getTitle().startsWith("price")) {
                price.add(indicator);
            } else if (indicator.getTitle().startsWith("quality")) {
                quality.add(indicator);
            } else if (indicator.getTitle().startsWith("growth")) {
                growth.add(indicator);
            }

        }

        result.put("가치 (가격/매출)", price);
        result.put("퀄리티 (가격/자산)", quality);
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
        strategyRepository.save(strategy);


        List<Indicator> indicators = indicatorRepository.findByIdIn(strategyAddDto.getIndicators());
        List<StrategyIndicator> changeList = new ArrayList<>();
        for (Indicator indicator : indicators) {
            Long count = indicator.getCount() + 1;
            indicator.setCount(count);

            StrategyIndicator strategyIndicator = new StrategyIndicator(strategy, indicator);
            changeList.add(strategyIndicator);
        }
        strategyIndicatorRepository.saveAll(changeList);

    }

    @Override
    @Transactional
    public void patchStrategy(Member member, StrategyAddDto strategyAddDto, Long strategyId) {

        Strategy strategy = strategyRepository.findById(strategyId).orElseThrow(() -> new NotFoundException("strategy not found"));

        if(strategy.getMember().getId() != member.getId()){
            throw new NotYourAuthorizationException();
        }

        if (strategyAddDto.getIndicators() != null) {
            List<StrategyIndicator> strategyIndicatorList = strategyIndicatorRepository.findByStrategy(strategy);
            strategyIndicatorRepository.deleteAll(strategyIndicatorList);

            List<Indicator> indicators = indicatorRepository.findByIdIn(strategyAddDto.getIndicators());
            List<StrategyIndicator> changed = new ArrayList<>();
            for (Indicator indicator : indicators) {
                StrategyIndicator strategyIndicator = new StrategyIndicator(strategy, indicator);
                changed.add(strategyIndicator);
            }
            strategyIndicatorRepository.saveAll(changed);
        }

        if (strategyAddDto.getStrategyName() != null) {
            strategy.setTitle(strategyAddDto.getStrategyName());
        }
        if (strategyAddDto.getAccess() != null) {
            strategy.setAccessType(strategyAddDto.getAccess());
        }
        if (strategyAddDto.getCumulativeReturn() != null) {
            strategy.setCumulativeReturn(strategyAddDto.getCumulativeReturn());
        }
        if (strategyAddDto.getCagr() != null) {
            strategy.setCagr(strategyAddDto.getCagr());
        }
        if (strategyAddDto.getSharpe() != null) {
            strategy.setSharpe(strategyAddDto.getSharpe());
        }


    }

    @Override
    @Transactional
    public void deleteStrategy(Member member, Long StrategyId) {
        Strategy strategy = strategyRepository.findById(StrategyId).orElseThrow(() -> new NotFoundException());

        if(strategy.getMember().getId() != member.getId()){
            throw new NotYourAuthorizationException();
        }

        List<StrategyIndicator> strategyIndicatorList = strategyIndicatorRepository.findByStrategy(strategy);
        strategyIndicatorRepository.deleteAll(strategyIndicatorList);

        strategyRepository.delete(strategy);

    }


}
