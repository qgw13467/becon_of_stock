package com.ssafy.beconofstock.member.entity;



import com.ssafy.beconofstock.authentication.provider.OAuthUserInfo;
import com.ssafy.beconofstock.board.entity.Board;
import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.contest.entity.Contest;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Member extends BaseEntity {

    public Member(OAuthUserInfo oAuthUserInfo){
        this.providerId = oAuthUserInfo.getProvider() + "_" + oAuthUserInfo.getProviderId();
        this.nickname = oAuthUserInfo.getName();
        this.role = Role.USER;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String providerId;

    private String nickname;

    private Role role;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Strategy> strategies = new ArrayList<>();

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<ContestMember> contestMembers = new ArrayList<>();

}
