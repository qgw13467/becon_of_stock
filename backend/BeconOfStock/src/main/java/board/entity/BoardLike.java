package board.entity;

import config.BaseEntity;
import member.entity.Member;
import strategy.entity.Strategy;

import javax.persistence.*;

@Entity
public class BoardLike extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
