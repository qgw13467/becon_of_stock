package com.ssafy.beconofstock.board.dto;

import com.ssafy.beconofstock.board.entity.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private String userNickname;
    private String content;
    private Long likeNum;
    private Long commentNum;
    private Long Depth;
    private LocalDateTime createDateTime;
//    private List<CommentResponseDto> children;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.userNickname = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.likeNum = comment.getLikeNum();
        this.commentNum = comment.getCommentNum();
        this.createDateTime = comment.getCreatedDateTime();
//        if (comment.getCommentNum() > 0) {
//            this.children = comment.getChildren().stream().map(x -> new CommentResponseDto(x.getChild())).collect(Collectors.toList());
//        }
    }

}
