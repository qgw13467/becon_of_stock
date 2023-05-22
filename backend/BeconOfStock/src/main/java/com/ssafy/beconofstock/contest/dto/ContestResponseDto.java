package com.ssafy.beconofstock.contest.dto;

import com.ssafy.beconofstock.contest.entity.Contest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ContestResponseDto {

    Long contestId;
    String title;
    String description;
    String content;
    Long type;
    LocalDateTime start_date_time;
    LocalDateTime end_date_time;

    public ContestResponseDto(Contest contest) {
        this.contestId = contest.getId();
        this.title = contest.getTitle();
        this.description = contest.getDescription();
        this.content = contest.getContent();
        this.type = contest.getType();
        this.start_date_time = contest.getStart_date_time();
        this.end_date_time = contest.getEnd_date_time();
    }
}
