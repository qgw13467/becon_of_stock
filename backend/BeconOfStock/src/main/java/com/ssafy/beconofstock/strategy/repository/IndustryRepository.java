package com.ssafy.beconofstock.strategy.repository;

import com.ssafy.beconofstock.backtest.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {


}
