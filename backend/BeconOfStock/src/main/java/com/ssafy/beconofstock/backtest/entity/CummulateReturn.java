package com.ssafy.beconofstock.backtest.entity;

import com.ssafy.beconofstock.strategy.entity.Strategy;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CummulateReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private Integer month;
    private Double strategyValue;
    private Double marketValue;
    @ManyToOne
    private Strategy strategy;

}
