package com.ssafy.beconofstock.strategy.repository;

import com.ssafy.beconofstock.strategy.entity.Strategy;
import com.ssafy.beconofstock.strategy.entity.StrategyIndicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StrategyIndicatorRepository extends JpaRepository<StrategyIndicator, Long> {

    List<StrategyIndicator> findByStrategy(Strategy strategy);

    @Query("SELECT si FROM StrategyIndicator si LEFT JOIN FETCH si.indicator WHERE si.strategy=:strategy")
    List<StrategyIndicator> findByStrategyFetch(@Param("strategy") Strategy strategy);
}
