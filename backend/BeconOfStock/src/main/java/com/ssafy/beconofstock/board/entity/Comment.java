package com.ssafy.beconofstock.board.entity;



import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@DynamicInsert
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @NotEmpty
    private String content;

    @ColumnDefault("0")
    private Long likeNum;
    private Long commentNum;


}
