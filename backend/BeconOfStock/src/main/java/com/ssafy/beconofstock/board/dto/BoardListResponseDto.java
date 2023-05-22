package com.ssafy.beconofstock.board.dto;

import com.ssafy.beconofstock.board.entity.Board;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardListResponseDto {
    @ApiModelProperty(example = "게시글 번호")
    Long boardId;
    @ApiModelProperty(example = "글 제목")
    String title;
    @ApiModelProperty(example = "작성일")
    LocalDateTime createDate;
    @ApiModelProperty(example = "작성자 아이디")
    Long memberId;
    @ApiModelProperty(example = "작성자 닉네임")
    String nickname;
    @ApiModelProperty(example = "조회수")
    Long hit;
    @ApiModelProperty(example = "댓글 수")
    Long commentNum;
    @ApiModelProperty(example = "좋아요 수")
    Long likeNum;

    public BoardListResponseDto(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.createDate = board.getCreatedDateTime();
        this.memberId = board.getMember().getId();
        this.nickname = board.getMember().getNickname();
        this.hit = board.getHit();
        this.commentNum = board.getCommentNum();
        this.likeNum = board.getLikeNum();
    }
}