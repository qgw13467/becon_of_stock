package com.ssafy.beconofstock.member.entity;


import com.ssafy.beconofstock.config.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member following;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member followed;
}

