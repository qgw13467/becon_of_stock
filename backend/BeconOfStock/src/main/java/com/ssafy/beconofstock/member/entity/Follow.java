package com.ssafy.beconofstock.member.entity;


import com.ssafy.beconofstock.config.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member following;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member followed;

    public void setFollowing(Member following) {
        this.following = following;
    }

    public void setFollowed(Member followed) {
        this.followed = followed;
    }
}

