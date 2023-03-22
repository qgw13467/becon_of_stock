package com.ssafy.beconofstock.board.dto;

import com.ssafy.beconofstock.board.entity.Comment;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResponseDto {


    private Long commentId;
    private String userNickname;
    private String content;
    private Long likeNum;
    private LocalDateTime createDateTime;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.userNickname = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.likeNum = comment.getLikeNum();
        this.createDateTime = comment.getCreatedDateTime();
    }

}
