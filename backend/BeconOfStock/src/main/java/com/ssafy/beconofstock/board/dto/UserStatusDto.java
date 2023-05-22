package com.ssafy.beconofstock.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserStatusDto {
    Boolean likeStatus;
    Boolean dibStatus;
    Boolean isAuthor;
    Boolean followStatus;

    public UserStatusDto(Boolean likeStatus, Boolean dibStatus, Boolean isAuthor,
        Boolean followStatus) {
        this.likeStatus = likeStatus;
        this.dibStatus = dibStatus;
        this.isAuthor = isAuthor;
        this.followStatus = followStatus;
    }
}