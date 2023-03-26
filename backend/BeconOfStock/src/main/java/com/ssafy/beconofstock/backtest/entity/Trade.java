package com.ssafy.beconofstock.backtest.entity;

import javax.persistence.*;

@Entity
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Transient
    private String indicator;
    @Transient
    private Double indicatorValue;
    @Transient
    private Integer ranking;

}
