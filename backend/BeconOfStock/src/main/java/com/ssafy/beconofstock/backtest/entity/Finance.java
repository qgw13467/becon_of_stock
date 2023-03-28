package com.ssafy.beconofstock.backtest.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Finance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer won;
    private Long currentAssets;
    private Long nonCurrentAssets;
    private Long totalAssets;
    private Long currentLiabilities;
    private Long nonCurrentLiabilities;
    private Long totalLiabilities;
    private Long totalCapital;
    private Long operatingRevenue;
    private Long operatingProfit;
    private Long netProfit;
    private String corName;
    private Integer rptYear;
    private Integer rptMonth;
}
