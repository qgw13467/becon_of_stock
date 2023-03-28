package com.ssafy.beconofstock.contest.dto;

import com.ssafy.beconofstock.contest.entity.Contest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContestResponseDto {

    Long contestId;
    String title;
    String description;
    String content;
    Long type;

    public ContestResponseDto(Contest contest) {
        this.contestId = contest.getId();
        this.title = contest.getTitle();
        this.description = contest.getDescription();
        this.content = contest.getContent();
        this.type = contest.getType();
    }
}
