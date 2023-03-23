package com.ssafy.beconofstock.contest.dto;

import com.ssafy.beconofstock.contest.entity.Contest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContestResponseDto {

    Long contestId;
    String description;
    String content;

    public ContestResponseDto(Contest contest) {
        this.contestId = contest.getId();
        this.description = contest.getDescription();
        this.content = contest.getContent();
    }
}
