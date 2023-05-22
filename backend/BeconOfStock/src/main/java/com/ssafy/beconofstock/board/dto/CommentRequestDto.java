package com.ssafy.beconofstock.board.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequestDto {
    @ApiModelProperty(example = "내용")
    private String content;
}
