package com.ssafy.beconofstock.board.controller;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.board.dto.BoardRequestDto;
import com.ssafy.beconofstock.board.dto.BoardResponseDto;
import com.ssafy.beconofstock.board.service.BoardServiceImpl;
import com.ssafy.beconofstock.member.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardServiceImpl boardService;

    
    // 글 작성 -> strategy 작성 이후 strategy도 연결 필요
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



}
