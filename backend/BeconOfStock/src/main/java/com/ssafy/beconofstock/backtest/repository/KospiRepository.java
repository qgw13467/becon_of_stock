package com.ssafy.beconofstock.backtest.repository;

import com.ssafy.beconofstock.backtest.entity.Kospi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KospiRepository extends JpaRepository<Kospi, Long> {

    @Query("SELECT k FROM Kospi k WHERE k.year>= :startYear and k.year<= :endYear order by k.year, k.month")
    List<Kospi> findByStartYearAndEndYear(@Param("startYear") Integer startYear, @Param("endYear") Integer endYear);

}
