package com.ssafy.beconofstock.backtest.repository;

import com.ssafy.beconofstock.backtest.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrateRepository extends JpaRepository<Trade, Long> {

    @Query("SELECT tr FROM Trade tr LEFT JOIN FETCH tr.finance WHERE tr.year = :year AND tr.month =:month")
    List<Trade> findByYearAndMonthFetch(Integer year, Integer month);

    List<Trade> findByYearAndMonth(Integer year, Integer month);
}
