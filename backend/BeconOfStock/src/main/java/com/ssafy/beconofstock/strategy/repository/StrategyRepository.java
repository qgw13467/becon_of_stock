package com.ssafy.beconofstock.strategy.repository;

import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StrategyRepository extends JpaRepository<Strategy, Long> {

    @Query("select sm from Strategy sm where sm.member=:member")
    Page<Strategy> findStrategyByMember (Member member, Pageable pageable);

    @Query("select s from Strategy s where s.id=:strategyId")
    Strategy findByStrategyId(Long strategyId);
}
