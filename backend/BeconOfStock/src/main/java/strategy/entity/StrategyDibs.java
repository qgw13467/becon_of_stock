package strategy.entity;

import config.BaseEntity;
import member.entity.Member;

import javax.persistence.*;

@Entity
public class StrategyDibs extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Strategy strategy;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;




}
