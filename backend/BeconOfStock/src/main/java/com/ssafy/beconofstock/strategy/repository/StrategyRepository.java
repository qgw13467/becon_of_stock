package com.ssafy.beconofstock.strategy.repository;

import com.ssafy.beconofstock.strategy.entity.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyRepository extends JpaRepository<Strategy, Long> {

}
