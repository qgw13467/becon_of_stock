package com.ssafy.beconofstock.contest.dto;

import com.ssafy.beconofstock.contest.entity.ContestMember;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContestMemberJoinResDto {
    Long contestMemberId;
    Long contestId;
    Long userId;
    Long strategyId;

    public ContestMemberJoinResDto(ContestMember contestMember) {
        this.contestMemberId = contestMember.getId();
        this.contestId = contestMember.getContest().getId();
        this.userId = contestMember.getMember().getId();
        this.strategyId = contestMember.getStrategy().getId();
    }

}
