package com.ssafy.beconofstock.strategy.service;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.backtest.dto.ChangeRateDto;
import com.ssafy.beconofstock.backtest.entity.CummulateReturn;
import com.ssafy.beconofstock.backtest.entity.Industry;
import com.ssafy.beconofstock.exception.NotFoundException;
import com.ssafy.beconofstock.exception.NotYourAuthorizationException;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.dto.*;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import com.ssafy.beconofstock.strategy.entity.StrategyIndicator;
import com.ssafy.beconofstock.strategy.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private final IndustryRepository industryRepository;
    private final EntityManager em;
    private final CummulationReturnRepository cummulationReturnRepository;

    @Override
    public StrategyDetailDto getStrategyDetail(Member member, Long strategyId) {

        Strategy strategy = strategyRepository.findById(strategyId).orElseThrow(() -> new NotFoundException());

//        if (strategy.getAccessType() == AccessType.PRIVATE
//                && strategy.getMember().getId() != member.getId()) {
//            throw new NotYourAuthorizationException();
//        }

        List<StrategyIndicator> strategyIndicatorList = strategyIndicatorRepository.findByStrategyFetch(strategy);

        List<Indicator> indicators = strategyIndicatorList.stream()
                .map(StrategyIndicator::getIndicator)
                .collect(Collectors.toList());

        StrategyDetailDto strategyDetailDto =
                StrategyDetailDto.builder()
                        .id(strategy.getId())
                        .title(strategy.getTitle())
                        .memberNickname(strategy.getMember().getNickname())
                        .memberId((strategy.getMember().getId()))
                        .indicators(indicators)
                        .strategyValues(toStrategyValues(strategy.getCummulateReturnList()))
                        .marketValues(toMarketValues(strategy.getCummulateReturnList()))
                        .representative(false)
                        .build();

        return strategyDetailDto;
    }

    @Override
    public IndicatorsDto getIndicators() {
        IndicatorsDto result = new IndicatorsDto();
        List<Map<String, Object>> factors = new ArrayList<>();

        List<Indicator> indicatorList = indicatorRepository.findAll();
        List<Indicator> price = new ArrayList<>();
        List<Indicator> quality = new ArrayList<>();
        List<Indicator> growth = new ArrayList<>();

        for (int i = 0; i < indicatorList.size(); i++) {
            Indicator indicator = indicatorList.get(i);
            em.detach(indicator);
            if (indicator.getTitle().startsWith("price")) {
                indicator.setTitle(getIndicatorName("price", indicator.getTitle()));
                price.add(indicator);
            } else if (indicator.getTitle().startsWith("quality")) {
                indicator.setTitle(getIndicatorName("quality", indicator.getTitle()));
                quality.add(indicator);
            } else if (indicator.getTitle().startsWith("growth")) {
                indicator.setTitle(getIndicatorName("growth", indicator.getTitle()));
                growth.add(indicator);
            }
        }

        factors.add(getMapByStringString(1L, List.of("가치 (가격/매출)", "주식가격과 회사의 매출을 통해 얼마나 저평가 되었는지 확인한 지표"), price));
        factors.add(getMapByStringString(2L, List.of("퀄리티 (매출/자산)", "회사의 매출과 회사의 자산을통해 얼마나 효율적으로 수익을 내는지 확인하는 지표"), quality));
        factors.add(getMapByStringString(3L, List.of("성장성 (이익 성장률)", "회사의 매출이 얼마나 빠르게 성장하는지 확인하는 지표"), growth));

        result.setFactors(factors);

        return result;
    }

    @Override
    public IndustriesDto getIndustries() {
        List<Industry> industries = industryRepository.findAll();

        return new IndustriesDto(industries);
    }

    private String getIndicatorName(String factor, String indicatorName) {
        return new StringBuffer(indicatorName).delete(0, factor.length()).toString();
    }

    private Map<String, Object> getMapByStringString(Long id, List<String> list, List<Indicator> indicators) {

        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", list.get(0));
        result.put("description", list.get(1));
        result.put("indicators", indicators);
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

        // 전략 저장 - id get
        Strategy strategy = new Strategy(member, strategyAddDto);
        strategy.setRepresentative(false);
        strategy = strategyRepository.save(strategy);

        // 누적 수익률 저장
        List<CummulateReturn> cummulateReturnList = new ArrayList<>();
        List<Double> marketValues = strategyAddDto.getMarketValues().stream().map(ChangeRateDto::getChangeRate).collect(Collectors.toList());
        List<Double> strategyValues = strategyAddDto.getStrategyValues().stream().map(ChangeRateDto::getChangeRate).collect(Collectors.toList());
        int year = strategyAddDto.getStrategyValues().get(0).getYear();
        int month = strategyAddDto.getStrategyValues().get(0).getMonth();

        for (int i = 0; i < marketValues.size(); i++) {
            cummulateReturnList.add(CummulateReturn.builder().strategyValue(strategyValues.get(i)).
                    marketValue(marketValues.get(i)).year(year).month(month).strategy(strategy).build());
        }
        cummulationReturnRepository.saveAll(cummulateReturnList);

        // 전략 저장
        strategy.setCummulateReturnList(cummulateReturnList);

        // 지표 저장
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

        if (strategy.getMember().getId() != member.getId()) {
            throw new NotYourAuthorizationException();
        }

        if (strategyAddDto.getIndicators() != null) {
            List<StrategyIndicator> strategyIndicatorList = strategyIndicatorRepository.findByStrategy(strategy);
            strategyIndicatorRepository.deleteAllInBatch(strategyIndicatorList);

            List<Indicator> indicators = indicatorRepository.findByIdIn(strategyAddDto.getIndicators());
            List<StrategyIndicator> changed = new ArrayList<>();
            for (Indicator indicator : indicators) {
                StrategyIndicator strategyIndicator = new StrategyIndicator(strategy, indicator);
                changed.add(strategyIndicator);
            }
            strategyIndicatorRepository.saveAll(changed);
        }

        strategy.setByStrategyAddDto(strategyAddDto);



//        if (strategyAddDto.getAccess() != null) {
//            strategy.setAccessType(strategyAddDto.getAccess());
//        }

    }

    @Override
    @Transactional
    public void deleteStrategy(Member member, Long StrategyId) {
        Strategy strategy = strategyRepository.findById(StrategyId).orElseThrow(() -> new NotFoundException());

        if (strategy.getMember().getId() != member.getId()) {
            throw new NotYourAuthorizationException();
        }

        List<StrategyIndicator> strategyIndicatorList = strategyIndicatorRepository.findByStrategy(strategy);
        strategyIndicatorRepository.deleteAllInBatch(strategyIndicatorList);

        strategyRepository.delete(strategy);
    }

    @Override
    @Transactional
    public Page<StrategyListDto> getStrategyMyList(OAuth2UserImpl user, Pageable pageable) {
        Page<Strategy> strategies = strategyRepository.findStrategyByMember(user.getMember(), pageable);

        PageImpl<StrategyListDto> result = new PageImpl<>(
                strategies.stream().map(strategy -> new StrategyListDto(
                        strategy, toStrategyValues(strategy.getCummulateReturnList()), toMarketValues(strategy.getCummulateReturnList()))
                ).collect(Collectors.toList()),
                pageable,
                strategies.getTotalPages());

        return result;
    }

    public List<ChangeRateDto> toMarketValues(List<CummulateReturn> cummulateReturnList) {
        List<ChangeRateDto> marketValues = new ArrayList<>();

        for (CummulateReturn cummulateReturn : cummulateReturnList) {
            marketValues.add(new ChangeRateDto(cummulateReturn.getMarketValue(), cummulateReturn.getYear(), cummulateReturn.getMonth()));
        }
        return marketValues;
    }

    public List<ChangeRateDto> toStrategyValues(List<CummulateReturn> cummulateReturnList) {
        List<ChangeRateDto> strategyValues = new ArrayList<>();

        for (CummulateReturn cummulateReturn : cummulateReturnList) {
            strategyValues.add(new ChangeRateDto(cummulateReturn.getStrategyValue(), cummulateReturn.getYear(), cummulateReturn.getMonth()));
        }
        return strategyValues;
    }


    @Override
    public Boolean updateRepresentative(OAuth2UserImpl user, Long strategyId) {
        Strategy strategy = strategyRepository.findByStrategyId(strategyId);
        List<Strategy> strategies = strategyRepository.findStrategyByMemberList(user.getMember());
        if (strategy == null && !strategy.getMember().getId().equals(user.getMember().getId())) {
            return false;
        }
        Boolean representative = strategy.getRepresentative();
        if (strategies.size() == 3 && !representative) {
            return false;
        }

        strategy.setRepresentative(!representative);
        strategyRepository.save(strategy);

        return true;
    }

    @Override
    public List<StrategyDetailDto> getRepresentative(OAuth2UserImpl user) {
        List<Strategy> strategies = strategyRepository.findStrategyByMemberList(user.getMember());
        List<StrategyDetailDto> strategyList = new ArrayList<>();
        for (Strategy strategy:strategies) {
            List<StrategyIndicator> strategyIndicatorList = strategyIndicatorRepository.findByStrategyFetch(strategy);

            List<Indicator> indicators = strategyIndicatorList.stream()
                    .map(StrategyIndicator::getIndicator)
                    .collect(Collectors.toList());

            StrategyDetailDto strategyDetailDto =
                    StrategyDetailDto.builder()
                            .id(strategy.getId())
                            .title(strategy.getTitle())
                            .memberNickname(strategy.getMember().getNickname())
                            .memberId((strategy.getMember().getId()))
                            .indicators(indicators)
                            .strategyValues(toStrategyValues(strategy.getCummulateReturnList()))
                            .marketValues(toMarketValues(strategy.getCummulateReturnList()))
                            .representative(strategy.getRepresentative())
                            .build();
            strategyList.add(strategyDetailDto);
        }

        return strategyList;
    }


}
