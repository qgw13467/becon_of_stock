package com.ssafy.beconofstock.backtest.repository;

import com.ssafy.beconofstock.backtest.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrateRepository extends JpaRepository<Trade,Long> {

    List<Trade> findByYearAndMonth(Integer year, Integer month);
}
