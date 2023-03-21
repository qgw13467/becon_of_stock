package board.entity;

import config.BaseEntity;
import member.entity.Member;

import javax.persistence.*;

@Entity
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String content;
    private Long likeNum;
    private Long commentNum;


}
