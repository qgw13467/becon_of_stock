package com.ssafy.beconofstock.strategy.entity;

import com.ssafy.beconofstock.config.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table()
@Getter
public class StrategyIndicator extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Strategy strategy;

    @ManyToOne(fetch = FetchType.LAZY)
    private Indicator indicator;

}

