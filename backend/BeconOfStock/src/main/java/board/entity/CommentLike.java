package board.entity;

import config.BaseEntity;
import strategy.entity.Strategy;

import javax.persistence.*;

@Entity
public class CommentLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Strategy strategy;

}
