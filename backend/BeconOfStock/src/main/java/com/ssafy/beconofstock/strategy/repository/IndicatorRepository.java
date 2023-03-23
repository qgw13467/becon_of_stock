package com.ssafy.beconofstock.strategy.repository;

import com.ssafy.beconofstock.strategy.entity.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndicatorRepository extends JpaRepository<Indicator,Long> {

    List<Indicator> findByIdIn(List<Long> ids);

}

