package com.ssafy.beconofstock.board.controller;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.board.dto.BoardRequestDto;
import com.ssafy.beconofstock.board.dto.BoardResponseDto;
import com.ssafy.beconofstock.board.dto.CommentRequestDto;
import com.ssafy.beconofstock.board.dto.CommentResponseDto;
import com.ssafy.beconofstock.board.service.BoardServiceImpl;
import com.ssafy.beconofstock.member.entity.Member;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = {"Board 관련 API"})
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardServiceImpl boardService;

    // 게시판
    // 글 작성 -> strategy 작성 이후 strategy도 연결 필요
    @ApiOperation(value = "글 작성",  notes =
            "새 글을 작성합니다.")
    @PostMapping("/")
    public ResponseEntity<?> writeBoard(@RequestBody BoardRequestDto board, @AuthenticationPrincipal OAuth2UserImpl user) {

        Member member = user.getMember();
        BoardResponseDto boardResponseDto = boardService.createBoard(member, board);

        return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
    }

    // 게시판 글 목록 조회
    @GetMapping("/")
    public ResponseEntity<?> getBoardList() {
        List<BoardResponseDto> boardList = boardService.getBoardList();
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    // 게시판 글 확인
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardDetail(@PathVariable Long boardId) {
        BoardResponseDto boardDetail = boardService.getBoardDetail(boardId);
        return new ResponseEntity<>(boardDetail, HttpStatus.OK);
    }

    // 게시판 글 수정
    @PatchMapping("/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto board, @AuthenticationPrincipal OAuth2UserImpl user) {
        BoardResponseDto newBoard = boardService.updateBoard(user, board, boardId);
        if (newBoard == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(newBoard, HttpStatus.CREATED);
    }

    // 게시판 글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<HttpStatus> deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal OAuth2UserImpl user) {
        Boolean result = boardService.deleteBoard(user, boardId);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // 댓글
    
    // 댓글 목록 조회
    @GetMapping("/{boardId}/comments")
    public ResponseEntity<?> getCommentList(@PathVariable Long boardId) {
        List<CommentResponseDto> commentList = boardService.getCommentList(boardId);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    // 댓글 디테일 조회
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<?> getCommentDetail(@PathVariable Long commentId) {
        CommentResponseDto comment = boardService.getComment(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    // 게시글 댓글 작성
    @PostMapping("/{boardId}/comments")
    public ResponseEntity<?> createComment(@PathVariable Long boardId, @RequestBody
        CommentRequestDto content, @AuthenticationPrincipal OAuth2UserImpl user) {
        CommentResponseDto comment = boardService.createComment(boardId, content, user);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    // 댓글 수정
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto content, @AuthenticationPrincipal OAuth2UserImpl user) {
        CommentResponseDto comment = boardService.updateComment(commentId, content, user);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal OAuth2UserImpl user) {
        Boolean result = boardService.deleteComment(commentId, user);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 대댓글 작성
    @PostMapping("/{boardId}/comments/{parentId}")
    public ResponseEntity<?> createReply(@PathVariable Long boardId, @PathVariable Long parentId, @RequestBody CommentRequestDto content, @AuthenticationPrincipal OAuth2UserImpl user) {
        CommentResponseDto comment = boardService.createComment(boardId, parentId, content, user);
        if (comment == null) {
            return new ResponseEntity<>("유효하지 않은 요청입니다.", HttpStatus.OK);
        }
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }



}
