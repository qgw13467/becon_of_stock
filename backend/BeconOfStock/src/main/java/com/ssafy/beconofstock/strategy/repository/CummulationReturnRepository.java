package com.ssafy.beconofstock.strategy.repository;

import com.ssafy.beconofstock.backtest.entity.CummulateReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CummulationReturnRepository extends JpaRepository<CummulateReturn, Long> {

}
