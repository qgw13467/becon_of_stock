package com.ssafy.beconofstock.contest.dto;

import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.contest.service.ContestMemberService;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContestMemberDto {
//    Long rank;
    Long userId;
    String userNickname;
    Long strategyId;

    public ContestMemberDto(ContestMember contestMember) {
//        this.rank = contestMember.getRanking();
        this.userId = contestMember.getMember().getId();
        this.userNickname = contestMember.getMember().getNickname();
        this.strategyId = contestMember.getStrategy().getId();
    }
}
