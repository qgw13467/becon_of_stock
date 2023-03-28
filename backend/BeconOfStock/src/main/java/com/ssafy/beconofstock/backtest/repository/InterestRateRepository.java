package com.ssafy.beconofstock.backtest.repository;

import com.ssafy.beconofstock.backtest.dto.YearMonth;
import com.ssafy.beconofstock.backtest.entity.InterestRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterestRateRepository extends JpaRepository<InterestRate, Long> {
    Optional<InterestRate> findByYearAndMonth(Long year, Long month);

    @Query("SELECT ir FROM InterestRate ir WHERE ir.year >= :startYear AND ir.year<=:endYear")
    List<InterestRate> findByYearMonthList(@Param("startYear") Integer startYear, @Param("endYear") Integer endYear);
}
