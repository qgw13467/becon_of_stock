package com.ssafy.beconofstock.backtest.repository;

import com.ssafy.beconofstock.backtest.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query("SELECT tr FROM Trade tr LEFT JOIN FETCH tr.finance WHERE tr.year = :year AND tr.month =:month")
    List<Trade> findByYearAndMonthFetch(@Param("year") Integer year, @Param("month") Integer month);

    List<Trade> findByYearAndMonth(Integer year, Integer month);

    @Query("SELECT tr FROM Trade tr WHERE tr.year = :year AND tr.month = :month AND tr.corcode IN (:corcodes)")
    List<Trade> findByYearAndMonthAndCorcodeList(@Param("year") Integer year, @Param("month") Integer month,
                                                 @Param("corcodes")List<String> corcodes);
}
