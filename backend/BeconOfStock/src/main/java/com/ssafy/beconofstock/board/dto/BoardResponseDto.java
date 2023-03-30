package com.ssafy.beconofstock.board.dto;

import com.ssafy.beconofstock.board.entity.Board;
import com.ssafy.beconofstock.strategy.dto.StrategyDetailDto;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardResponseDto {
    @ApiModelProperty(example = "게시글 번호")
    Long boardId;
    @ApiModelProperty(example = "작성자 id")
    Long memberId;
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
    StrategyDetailDto strategy;
    @ApiModelProperty(example = "댓글 수")
    Long commentNum;
    @ApiModelProperty(example = "좋아요 수")
    Long likeNum;
    @ApiModelProperty(example = "좋아요 상태")
    Boolean likeStatus;
    @ApiModelProperty(example = "찜하기 상태")
    Boolean dibStatus;

    public BoardResponseDto(Board board, List<Indicator> indicators, Boolean likeStatus, Boolean dibStatus) {
        this.boardId = board.getId();
        this.createDate = board.getCreatedDateTime();
        this.memberId = board.getMember().getId();
        this.nickname = board.getMember().getNickname();
        this.hit = board.getHit();
        this.title = board.getTitle();
        this.content = board.getContent();
        if (board.getStrategy() != null) {
            this.strategy = strategyDetailDto(board.getStrategy(), indicators);
        }
        this.likeNum = board.getLikeNum();
        this.commentNum = board.getCommentNum();
        this.likeStatus = likeStatus;
        this.dibStatus = dibStatus;
    }

    public BoardResponseDto(Board board, Boolean likeStatus, Boolean dibStatus) {
        this.boardId = board.getId();
        this.createDate = board.getCreatedDateTime();
        this.memberId = board.getMember().getId();
        this.nickname = board.getMember().getNickname();
        this.hit = board.getHit();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.likeNum = board.getLikeNum();
        this.commentNum = board.getCommentNum();
        this.likeStatus = likeStatus;
        this.dibStatus = dibStatus;
    }

    public StrategyDetailDto strategyDetailDto(Strategy strategy, List<Indicator> indicators) {

        return  StrategyDetailDto.builder()
                .id(strategy.getId())
                .title(strategy.getTitle())
                .sharpe(strategy.getSharpe())
                .cagr(strategy.getCagr())
                .cumulativeReturn(strategy.getCumulativeReturn())
                .memberNickname(strategy.getMember().getNickname())
                .memberId((strategy.getMember().getId()))
                .indicators(indicators)
                .access(strategy.getAccessType())
                .build();
    }
}
