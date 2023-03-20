package com.ssafy.beconofstock.member.dto;

import com.ssafy.beconofstock.member.entity.Follow;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowedDto {

    private Long userId;
    private String nickname;

    public FollowedDto(Follow follow){

        this.userId=follow.getFollowed().getId();
        this.nickname=follow.getFollowed().getNickname();
    }

}
