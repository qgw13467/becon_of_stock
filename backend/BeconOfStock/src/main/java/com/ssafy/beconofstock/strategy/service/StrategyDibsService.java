package com.ssafy.beconofstock.strategy.service;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.strategy.dto.StrategyDibsDto;
import com.ssafy.beconofstock.strategy.entity.StrategyDibs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StrategyDibsService {

    /**
     * 전략 찜하기
     * @param strategyId
     * @param user
     */
    void dibsStrategy(Long strategyId, OAuth2UserImpl user);

    /**
     * 찜 전략 삭제하기
     *
     * @param strategyDibsId
     * @param user
     * @return
     */
    Boolean deleteDibs(Long strategyDibsId, OAuth2UserImpl user);

    /**
     * 나의 찜 전략 리스트
     * @param user
     * @param pageable
     * @return
     */
    Page<StrategyDibsDto> getStrategyDibsMyList(OAuth2UserImpl user, Pageable pageable);
}
