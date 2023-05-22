package com.ssafy.beconofstock.contest.dto;

import com.ssafy.beconofstock.contest.entity.Contest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContestMemberJoinReqDto {

    private Long contestId;
    private Long strategyId;


}
