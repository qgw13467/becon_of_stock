package com.ssafy.beconofstock.contest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContestRequestDto {

    private Long contestId;
    private String description;
    private String content;
}
