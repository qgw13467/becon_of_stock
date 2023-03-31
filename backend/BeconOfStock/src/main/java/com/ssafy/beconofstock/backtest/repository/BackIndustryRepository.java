package com.ssafy.beconofstock.backtest.repository;

import com.ssafy.beconofstock.backtest.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BackIndustryRepository extends JpaRepository<Industry, Long> {

    List<Industry> findByIdIn(List<Long> ids);
}
