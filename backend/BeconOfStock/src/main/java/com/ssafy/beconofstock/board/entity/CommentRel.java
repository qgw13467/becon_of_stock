package com.ssafy.beconofstock.board.entity;


import com.ssafy.beconofstock.config.BaseEntity;

import javax.persistence.*;

@Entity
public class CommentRel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment prent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment child;
}
