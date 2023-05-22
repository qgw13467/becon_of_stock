package com.ssafy.beconofstock.contest.dto;

import com.ssafy.beconofstock.contest.entity.ContestMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContestHistoryDto {

    private Long contestId;
    private String contestName;
//    private Long result;
    private Long strategyId;

    public ContestHistoryDto(ContestMember contestMember){
        this.contestId = contestMember.getContest().getId();
        this.contestName = contestMember.getContest().getTitle();
//        this.result = contestMember.getRanking();
        this.strategyId = contestMember.getStrategy().getId();

    }

}
