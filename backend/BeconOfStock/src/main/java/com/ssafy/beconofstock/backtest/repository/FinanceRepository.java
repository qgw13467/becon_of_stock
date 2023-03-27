package com.ssafy.beconofstock.backtest.repository;

import com.ssafy.beconofstock.backtest.entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, Long> {

    Optional<Finance> findByRptYearAndRptMonthAndCorName(Integer year, Integer month, String corName);

    List<Finance> findByRptYearAndRptMonth(Integer year, Integer month);

    @Query("SELECT f FROM Finance f WHERE f.rptYear = :year AND f.rptMonth IN (:months)")
    List<Finance> findByRptYearAndRptMonth(@Param("year") Integer year, @Param("months") List<Integer> months);

    @Query("SELECT f FROM Finance f WHERE f.rptYear = :year AND f.rptMonth IN (:months) and f.corName =:corName")
    Optional<Finance> findByRptYearAndRptMonthAndCorName(@Param("year") Integer year, @Param("months") List<Integer> months,
                                                     @Param("corName") String corName);
}
