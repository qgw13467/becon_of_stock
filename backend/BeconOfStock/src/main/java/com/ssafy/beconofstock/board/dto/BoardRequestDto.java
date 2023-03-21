package com.ssafy.beconofstock.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardRequestDto {
    private Long strategyId;
    private String title;
    private String content;
}
