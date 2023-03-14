package com.ssafy.beconofstock.member.entity;



import com.ssafy.beconofstock.board.entity.Board;
import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.contest.entity.Contest;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nickname;

    private Role role;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Strategy> strategies = new ArrayList<>();

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<ContestMember> contestMembers = new ArrayList<>();

}
