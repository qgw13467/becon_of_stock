package com.ssafy.beconofstock.member.dto;

import com.ssafy.beconofstock.contest.dto.ContestHistoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class UserInfoDto {
    private String nickname;
    private Long followNum;

    List<ContestHistoryDto> contestHistory;




}
