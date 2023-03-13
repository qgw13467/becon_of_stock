package contest.entity;

import config.BaseEntity;
import member.entity.Member;
import strategy.entity.Strategy;

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
