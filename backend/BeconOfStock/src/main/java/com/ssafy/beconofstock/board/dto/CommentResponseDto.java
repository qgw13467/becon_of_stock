package com.ssafy.beconofstock.board.dto;

import com.ssafy.beconofstock.board.entity.Comment;
import com.ssafy.beconofstock.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResponseDto {

    @ApiModelProperty(example = "댓글 아이디")
    private Long commentId;
    @ApiModelProperty(example = "작성자 아이디")
    private Long memberId;
    @ApiModelProperty(example = "작성자 닉네임")
    private String userNickname;
    @ApiModelProperty(example = "내용")
    private String content;
    @ApiModelProperty(example = "좋아요 수")
    private Long likeNum;
    @ApiModelProperty(example = "댓글 숫자")
    private Long commentNum;
    @ApiModelProperty(example = "수정여부")
    private Boolean modified;
    @ApiModelProperty(example = "작성일")
    private LocalDateTime createDateTime;
    @ApiModelProperty(example = "작성자 여부")
    private Boolean isAuthor;
    @ApiModelProperty(example = "대댓글")
    private List<CommentResponseDto> children;

    public CommentResponseDto(Comment comment, Member member) {
        this.commentId = comment.getId();
        this.memberId = comment.getMember().getId();
        this.userNickname = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.likeNum = comment.getLikeNum();
        this.commentNum = comment.getCommentNum();
        this.modified = comment.getModified();
        this.createDateTime = comment.getCreatedDateTime();
        this.isAuthor = comment.getMember().getId().equals(member.getId());
    }
    public CommentResponseDto(Comment comment, Member member, List<CommentResponseDto> children) {
        this.commentId = comment.getId();
        this.memberId = comment.getMember().getId();
        this.userNickname = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.likeNum = comment.getLikeNum();
        this.commentNum = comment.getCommentNum();
        this.createDateTime = comment.getCreatedDateTime();
        this.modified = comment.getModified();
        this.isAuthor = comment.getMember().getId().equals(member.getId());
        this.children = children;
    }

}
