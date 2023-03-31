package com.ssafy.beconofstock.backtest.repository;

import com.ssafy.beconofstock.backtest.entity.CorporationIndustry;
import com.ssafy.beconofstock.backtest.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorporationIndustryRepository extends JpaRepository<CorporationIndustry, Long> {

    @Query("select ci.industry from CorporationIndustry ci where ci.cor_name = :corName ")
    Optional<Industry> findByCorName(@Param("corName") String corName);

}
