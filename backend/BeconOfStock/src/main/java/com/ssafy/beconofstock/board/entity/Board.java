package com.ssafy.beconofstock.board.entity;


import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;

import javax.persistence.*;

@Entity
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String title;
    private String content;
    private Long hit;
    private Long likeNum;


}
