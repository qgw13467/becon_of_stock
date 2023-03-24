package com.ssafy.beconofstock.board.dto;

import com.ssafy.beconofstock.board.entity.Board;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class BoardListResponseDto {

    @ApiModelProperty(example = "현재 페이지")
    int pageNumber;
    @ApiModelProperty(example = "현재 페이지 게시글 수")
    int numberOfElements;
    @ApiModelProperty(example = "전체 페이지 수")
    int totalPages;
    @ApiModelProperty(example = "전체 게시글 수")
    Long totalElements;
    @ApiModelProperty(example = "게시글 정보 : 게시글 아이디, 제목, 작성자 닉네임, 작성일, 조회수, 댓글 수 , 좋아요 수 반환")
    List<ContentInfo> content;

    public BoardListResponseDto(Page<Board> boardPage) {
        this.pageNumber = boardPage.getPageable().getPageNumber();
        this.numberOfElements = boardPage.getNumberOfElements();
        this.totalPages = boardPage.getTotalPages();
        this.totalElements = boardPage.getTotalElements();
        this.content = boardPage.getContent().stream().map(ContentInfo::new).collect(Collectors.toList());
    }

    @Data
    static class ContentInfo {
        @ApiModelProperty(example = "게시글 번호")
        Long boardId;
        @ApiModelProperty(example = "글 제목")
        String title;
        @ApiModelProperty(example = "작성일")
        LocalDateTime createDate;
        @ApiModelProperty(example = "작성자 닉네임")
        String nickname;
        @ApiModelProperty(example = "조회수")
        Long hit;
        @ApiModelProperty(example = "댓글 수")
        Long commentNum;
        @ApiModelProperty(example = "좋아요 수")
        Long likeNum;

        public ContentInfo(Board board) {
            this.boardId = board.getId();
            this.title = board.getTitle();
            this.createDate = board.getCreatedDateTime();
            this.nickname = board.getMember().getNickname();
            this.hit = board.getHit();
            this.commentNum = board.getCommentNum();
            this.likeNum = board.getLikeNum();
        }
    }



}