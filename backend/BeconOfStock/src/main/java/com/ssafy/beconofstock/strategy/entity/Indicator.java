package com.ssafy.beconofstock.strategy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private Long count;

    private String description;

    @Enumerated(EnumType.STRING)
    private SortType sortType;

}
