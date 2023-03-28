package com.ssafy.beconofstock.contest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContestRequestDto {

    private String title;
    private String description;
    private String content;
    private Long type;
}
