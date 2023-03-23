package com.ssafy.beconofstock.strategy.service;

import com.ssafy.beconofstock.exception.NotFoundException;
import com.ssafy.beconofstock.exception.NotYourAuthorizationException;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.dto.IndicatorsDto;
import com.ssafy.beconofstock.strategy.dto.StrategyAddDto;
import com.ssafy.beconofstock.strategy.dto.StrategyDetailDto;
import com.ssafy.beconofstock.strategy.entity.AccessType;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import com.ssafy.beconofstock.strategy.entity.StrategyIndicator;
import com.ssafy.beconofstock.strategy.repository.IndicatorRepository;
import com.ssafy.beconofstock.strategy.repository.StrategyIndicatorRepository;
import com.ssafy.beconofstock.strategy.repository.StrategyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StrategyServiceImpl implements StrategyService {

    private final StrategyRepository strategyRepository;
    private final StrategyIndicatorRepository strategyIndicatorRepository;
    private final IndicatorRepository indicatorRepository;

    private final EntityManager em;

    @Override
    public StrategyDetailDto getStrategyDetail(Member member, Long strategyId) {

        Strategy strategy = strategyRepository.findById(strategyId).orElseThrow(() -> new NotFoundException());

        if(strategy.getAccessType() == AccessType.PRIVATE
                && strategy.getMember().getId()!= member.getId()){
            throw new NotYourAuthorizationException();
        }

        List<StrategyIndicator> strategyIndicatorList = strategyIndicatorRepository.findBySrategyFetch(strategy);

        List<Indicator> indicators = strategyIndicatorList.stream()
                .map(StrategyIndicator::getIndicator)
                .collect(Collectors.toList());

        StrategyDetailDto strategyDetailDto =
                StrategyDetailDto.builder()
                        .id(strategy.getId())
                        .title(strategy.getTitle())
                        .sharpe(strategy.getSharpe())
                        .cagr(strategy.getCagr())
                        .cumulativeReturn(strategy.getCumulativeReturn())
                        .memberNickname(strategy.getMember().getNickname())
                        .memberId((strategy.getMember().getId()))
                        .indicators(indicators)
                        .access(strategy.getAccessType())
                        .build();

        return strategyDetailDto;
    }

    @Override
    public IndicatorsDto getIndicators() {
        IndicatorsDto result = new IndicatorsDto();
        Map<String, List<Indicator>> indicators = new HashMap<>();
        List<Map<String,String>> fators = new ArrayList<>();

        List<Indicator> indicatorList = indicatorRepository.findAll();
        List<Indicator> price = new ArrayList<>();
        List<Indicator> quality = new ArrayList<>();
        List<Indicator> growth = new ArrayList<>();

        for (int i = 0; i < indicatorList.size(); i++) {
            Indicator indicator = indicatorList.get(i);
            em.detach(indicator);
            if (indicator.getTitle().startsWith("price")) {
                indicator.setTitle(getIndicatorName("price",indicator.getTitle()));
                price.add(indicator);
            } else if (indicator.getTitle().startsWith("quality")) {
                indicator.setTitle(getIndicatorName("quality",indicator.getTitle()));
                quality.add(indicator);
            } else if (indicator.getTitle().startsWith("growth")) {
                indicator.setTitle(getIndicatorName("growth",indicator.getTitle()));
                growth.add(indicator);
            }
        }

        indicators.put("가치 (가격/매출)", price);
        indicators.put("퀄리티 (매출/자산)", quality);
        indicators.put("성장성 (이익 성장률)", growth);


        fators.add(getMapByStringString("가치 (가격/매출)", "주식가격과 회사의 매출을 통해 얼마나 저평가 되었는지 확인한 지표"));
        fators.add(getMapByStringString("퀄리티 (매출/자산)", "회사의 매출과 회사의 자산을통해 얼마나 효율적으로 수익을 내는지 확인하는 지표"));
        fators.add(getMapByStringString("성장성 (이익 성장률)", "회사의 매출이 얼마나 빠르게 성장하는지 확인하는 지표"));

        result.setFators(fators);
        result.setIndicators(indicators);

        return result;
    }
    private String getIndicatorName(String factor, String indicatorName){
        return new StringBuffer(indicatorName).delete(0, factor.length()).toString();
    }

    private Map<String,String> getMapByStringString(String key, String value){
        Map<String,String> result = new HashMap<>();
        result.put(key,value);
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
