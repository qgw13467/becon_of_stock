package com.ssafy.beconofstock.member.entity;



import com.ssafy.beconofstock.authentication.provider.OAuthUserInfo;
import com.ssafy.beconofstock.board.entity.Board;
import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.contest.entity.Contest;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Member extends BaseEntity {

    public Member(OAuthUserInfo oAuthUserInfo){
        this.providerId = oAuthUserInfo.getProvider() + "_" + oAuthUserInfo.getProviderId();
        this.nickname = oAuthUserInfo.getName();
        this.profileImg = oAuthUserInfo.getProfileImg();
        this.role = Role.USER;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String providerId;
    private String nickname;
    @ColumnDefault("0")
    private Long followNum;
    @ColumnDefault("0")
    private Long followerNum;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ColumnDefault("")
    private String profileImg;

    private boolean expired;

//    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
//    private List<Board> boards = new ArrayList<>();

//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    private List<Strategy> strategies = new ArrayList<>();

//    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
//    private List<ContestMember> contestMembers = new ArrayList<>();

}
