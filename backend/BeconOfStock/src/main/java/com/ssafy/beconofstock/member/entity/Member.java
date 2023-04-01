package com.ssafy.beconofstock.member.entity;


import com.ssafy.beconofstock.authentication.provider.OAuthUserInfo;
import com.ssafy.beconofstock.config.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Member extends BaseEntity {

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
    private String profileImg;
    private boolean expired;

    public Member(OAuthUserInfo oAuthUserInfo) {
        this.providerId = oAuthUserInfo.getProvider() + "_" + oAuthUserInfo.getProviderId();
        this.nickname = oAuthUserInfo.getName();
        this.role = Role.USER;
    }

//    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
//    private List<Board> boards = new ArrayList<>();

//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    private List<Strategy> strategies = new ArrayList<>();

//    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
//    private List<ContestMember> contestMembers = new ArrayList<>();

}
