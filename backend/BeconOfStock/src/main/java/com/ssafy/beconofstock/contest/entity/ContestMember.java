package com.ssafy.beconofstock.contest.entity;



import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.entity.Strategy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ContestMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contest contest;


    @ManyToOne(fetch = FetchType.LAZY)
    private Strategy strategy;

    private LocalDateTime participateDateTime;


}
