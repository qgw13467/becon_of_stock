package com.ssafy.beconofstock.strategy.service;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import com.ssafy.beconofstock.strategy.entity.StrategyDibs;
import com.ssafy.beconofstock.strategy.repository.StrategyDibsRepository;
import com.ssafy.beconofstock.strategy.repository.StrategyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StrategyDibsServiceImpl implements StrategyDibsService{

    private final StrategyRepository strategyRepository;
    private final StrategyDibsRepository strategyDibsRepository;


    @Override
    public void dibsStrategy(Long strategyId, OAuth2UserImpl user) {
        Strategy strategy = strategyRepository.findById(strategyId).orElse(null);
        Member member = user.getMember();
        StrategyDibs dibs = StrategyDibs.builder()
                .member(member)
                .strategy(strategy)
                .build();
        strategyDibsRepository.save(dibs);
    }

    @Override
    public Boolean deleteDibs(Long strategyDibsId, OAuth2UserImpl user) {
        StrategyDibs dibs = strategyDibsRepository.findById(strategyDibsId).orElse(null);
        if (!dibs.getMember().getId().equals(user.getMember().getId())) {
            return false;
        }
        strategyDibsRepository.delete(dibs);
        return true;
    }

    @Override
    public Page<StrategyDibs> getStrategyDibsMyList(OAuth2UserImpl user, Pageable pageable) {
        Page<StrategyDibs> result = strategyDibsRepository.findByMember(user.getMember(), pageable);
        return result;
    }
}
