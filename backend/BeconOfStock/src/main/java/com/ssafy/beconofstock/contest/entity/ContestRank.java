package com.ssafy.beconofstock.contest.entity;

import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class ContestRank extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contest contest;

    @ManyToOne(fetch = FetchType.LAZY)
    private Strategy strategy;

    @ManyToOne(fetch = FetchType.LAZY)
    private ContestMember contestMember;

    private Long ranking;

}
