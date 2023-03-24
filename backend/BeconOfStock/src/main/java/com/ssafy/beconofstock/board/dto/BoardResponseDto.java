package com.ssafy.beconofstock.board.dto;

import com.ssafy.beconofstock.board.entity.Board;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardResponseDto {

    @ApiModelProperty(example = "게시글 번호")
    Long boardId;
    @ApiModelProperty(example = "글 제목")
    String title;
    @ApiModelProperty(example = "글 내용")
    String content;
    @ApiModelProperty(example = "작성자 닉네임")
    String nickname;
    @ApiModelProperty(example = "전략")
    Strategy strategy;
    @ApiModelProperty(example = "조회수")
    Long hit;
    @ApiModelProperty(example = "댓글 수")
    Long commentNum;
    @ApiModelProperty(example = "좋아요 수")
    Long likeNum;

    public BoardResponseDto(Board board) {
            this.boardId = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.nickname = board.getMember().getNickname();
            this.strategy = board.getStrategy();
            this.hit = board.getHit();
            this.likeNum = board.getLikeNum();
            this.commentNum = board.getCommentNum();
    }
}
