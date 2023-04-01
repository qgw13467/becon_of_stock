package com.ssafy.beconofstock.member.dto;

import com.ssafy.beconofstock.member.entity.Follow;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowedDto {

    private Long userId;
    private String nickname;
    private String profileImg;

    public FollowedDto(Follow follow) {

        this.userId = follow.getFollowing().getId();
        this.nickname = follow.getFollowing().getNickname();
        this.profileImg = follow.getFollowing().getProfileImg();
    }

}
