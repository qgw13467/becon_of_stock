package com.ssafy.beconofstock.strategy.repository;

import com.ssafy.beconofstock.backtest.entity.CummulateReturn;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CummulationReturnRepository extends JpaRepository<CummulateReturn, Long> {

    @Query("select cr from CummulateReturn cr where cr.strategy.id=:strategyId")
    Page<CummulateReturn> findCummulateReturnByStrategyId(@Param("strategyId") Long strategyId);
}
