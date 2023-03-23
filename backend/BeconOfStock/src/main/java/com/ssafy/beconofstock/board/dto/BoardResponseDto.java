package com.ssafy.beconofstock.board.dto;

import com.ssafy.beconofstock.board.entity.Board;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardResponseDto {

    Long boardId;
    String title;
    String content;
    String nickname;
    Strategy strategy;
    Long hit;
    Long commentNum;
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
