package com.ssafy.beconofstock.backtest.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CorporationIndustry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cor_name;
    @ManyToOne
    private Industry industry;
}
