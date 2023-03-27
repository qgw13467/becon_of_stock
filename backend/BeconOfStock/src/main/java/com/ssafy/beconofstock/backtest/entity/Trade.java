package com.ssafy.beconofstock.backtest.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Finance finance;
    private String corcode;
    private String corname;
    private Long corclose;
    private String dept;
    private Long changecode;
    private double changes;
    private double chagesratio;
    private Long volume;
    private Long amount;
    private Long coropen;
    private Long high;
    private Long low;
    private Long marcap;
    private Long stocks;
    private String market;
    private String marketid;
    private Long corrank;
    private Integer year;
    private Integer month;

    //indicator

    private Double pricePER;
    private Double pricePBR;
    private Double pricePSR;
    private Double pricePOR;
    private Double qualityROE;
    private Double qualityROA;
    private Double growth3MonthTake;
    private Double growth12MonthTake;
    private Double growth3MonthOperatingProfit;
    private Double growth12MonthOperatingProfit;
    private Double growth3MonthNetProfit;
    private Double growth12MonthNetProfit;


    @Transient
    private String indicator;
    @Transient
    private Double indicatorValue;
    @Transient
    private Integer ranking;

}
