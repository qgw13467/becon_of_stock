package com.ssafy.beconofstock.board.dto;

import com.ssafy.beconofstock.board.entity.Board;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardResponseDto {

    @ApiModelProperty(example = "게시글 번호")
    Long boardId;
    @ApiModelProperty(example = "작성자 닉네임")
    String nickname;
    @ApiModelProperty(example = "조회수")
    Long hit;
    @ApiModelProperty(example = "작성일")
    LocalDateTime createDate;
    @ApiModelProperty(example = "글 제목")
    String title;
    @ApiModelProperty(example = "글 내용")
    String content;
    @ApiModelProperty(example = "전략")
    Strategy strategy;
    @ApiModelProperty(example = "댓글 수")
    Long commentNum;
    @ApiModelProperty(example = "좋아요 수")
    Long likeNum;
    @ApiModelProperty(example = "좋아요 상태")
    Boolean likeStatus;
    @ApiModelProperty(example = "찜하기 상태")
    Boolean dibStatus;

    public BoardResponseDto(Board board, Boolean likeStatus, Boolean dibStatus) {
        this.boardId = board.getId();
        this.createDate = board.getCreatedDateTime();
        this.nickname = board.getMember().getNickname();
        this.hit = board.getHit();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.strategy = board.getStrategy();
        this.likeNum = board.getLikeNum();
        this.commentNum = board.getCommentNum();
        this.likeStatus = likeStatus;
        this.dibStatus = dibStatus;
    }
}
