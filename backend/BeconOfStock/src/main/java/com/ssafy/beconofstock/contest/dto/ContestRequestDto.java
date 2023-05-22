package com.ssafy.beconofstock.contest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ContestRequestDto {

    private String title;
    private String description;
    private String content;
    private Long type;
    private LocalDateTime start_date_time;
    private LocalDateTime end_date_time;
}
