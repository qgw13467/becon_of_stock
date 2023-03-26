package com.ssafy.beconofstock.backtest.repository;

import com.ssafy.beconofstock.backtest.entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepository extends JpaRepository<Finance,Long> {
}
