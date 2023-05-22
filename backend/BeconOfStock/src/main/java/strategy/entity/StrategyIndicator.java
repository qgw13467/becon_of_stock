package com.ssafy.beconofstock.strategy.entity;

import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.strategy.repository.IndicatorRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table()
@Getter
@Setter
@NoArgsConstructor
public class StrategyIndicator extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Strategy strategy;

    @ManyToOne(fetch = FetchType.LAZY)
    private Indicator indicator;

    public StrategyIndicator(Strategy strategy, Indicator indicator){
        this.strategy = strategy;
        this.indicator = indicator;
    }

}

