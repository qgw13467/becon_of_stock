package com.ssafy.beconofstock.member.dto;

import com.ssafy.beconofstock.member.entity.Follow;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowingDto {

    private Long userId;
    private String nickname;

    public FollowingDto(Follow follow){
        this.userId=follow.getFollowing().getId();
        this.nickname=follow.getFollowing().getNickname();
    }
}
