package com.ssafy.beconofstock.board.controller;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.board.dto.BoardListResponseDto;
import com.ssafy.beconofstock.board.dto.BoardRequestDto;
import com.ssafy.beconofstock.board.dto.BoardResponseDto;
import com.ssafy.beconofstock.board.dto.CommentRequestDto;
import com.ssafy.beconofstock.board.dto.CommentResponseDto;
import com.ssafy.beconofstock.board.service.BoardServiceImpl;
import com.ssafy.beconofstock.member.entity.Member;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = {"Board 관련 API"})
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardServiceImpl boardService;

    @ApiOperation(value = "글 목록 조회", notes = "커뮤니티 게시판 전체 목록을 조회합니다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공입니다", response = BoardResponseDto.class)
    })
    @GetMapping("/")
    public ResponseEntity<?> getBoardList(
        @ApiParam(name = "page", value = "페이지 번호. 미입력시 0에서 시작", example = "0") @RequestParam(defaultValue = "0") int page,
        @ApiParam(name = "direction", value = "정렬방향. 정렬 내림차순 false, 오름차순 true", example = "false") @RequestParam(defaultValue = "false") Boolean direction,
        @ApiParam(name = "property", value = "정렬조건", allowableValues = "id, title, hit, likeNum, commentNum", example = "id", defaultValue = "id") @RequestParam(defaultValue = "id") String property) {
        Page<BoardListResponseDto> paging = boardService.getBoardList(page, direction, property);
        return new ResponseEntity<>(paging, HttpStatus.OK);
    }


    @ApiOperation(value = "글 작성",  notes = "새 글을 작성합니다.")
    @ApiResponses({
        @ApiResponse(code = 201, message = "성공입니다", response = BoardResponseDto.class),
        @ApiResponse(code = 403, message = "정보를 찾을 수 없습니다.")
    })
    @PostMapping("/")
    public ResponseEntity<?> writeBoard(@RequestBody BoardRequestDto board, @AuthenticationPrincipal OAuth2UserImpl user) {

        Member member = user.getMember();
        BoardResponseDto boardResponseDto = boardService.createBoard(member, board);

        return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "글 상세보기", notes = "커뮤니티 게시판 글의 상세 내용을 조회합니다.")
    @ApiResponses({
        @ApiResponse(code=200, message = "성공입니다", response = BoardResponseDto.class)
    })
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardDetail(@PathVariable Long boardId, @AuthenticationPrincipal OAuth2UserImpl user) {
        BoardResponseDto boardDetail = boardService.getBoardDetail(boardId, user);
        return new ResponseEntity<>(boardDetail, HttpStatus.OK);
    }

    // 게시판 글 수정
    @ApiOperation(value = "글 수정하기", notes = "작성한 글을 수정합니다.")
    @ApiResponses({
        @ApiResponse(code = 201, message = "성공입니다", response = BoardResponseDto.class),
        @ApiResponse(code = 403, message = "권한이 없습니다.")
    })
    @PatchMapping("/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto board, @AuthenticationPrincipal OAuth2UserImpl user) {
        BoardResponseDto newBoard = boardService.updateBoard(user, board, boardId);
        if (newBoard == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(newBoard, HttpStatus.CREATED);
    }

    // 게시판 글 삭제
    @ApiOperation(value = "글 삭제하기", notes = "작성한 글을 삭제합니다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공입니다"),
        @ApiResponse(code = 403, message = "권한이 없습니다.")
    })
    @DeleteMapping("/{boardId}")
    public ResponseEntity<HttpStatus> deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal OAuth2UserImpl user) {
        Boolean result = boardService.deleteBoard(user, boardId);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 목록 조회하기", notes = "해당 글에 작성된 댓글 목록을 조회합니다")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공입니다", response = CommentResponseDto.class)
    })
    @GetMapping("/{boardId}/comments")
    public ResponseEntity<?> getCommentList(@PathVariable Long boardId) {
        List<CommentResponseDto> commentList = boardService.getCommentList(boardId);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 상세 조회", notes = "댓글의 상세 내용을 조회합니다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공입니다", response = CommentResponseDto.class)
    })
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<?> getCommentDetail(@PathVariable Long commentId) {
        CommentResponseDto comment = boardService.getComment(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 작성", notes = "새 댓글을 작성합니다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공입니다", response = CommentResponseDto.class)
    })
    @PostMapping("/{boardId}/comments")
    public ResponseEntity<?> createComment(@PathVariable Long boardId, @RequestBody
        CommentRequestDto content, @AuthenticationPrincipal OAuth2UserImpl user) {
        List<CommentResponseDto> comment = boardService.createComment(boardId, content, user);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정합니다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공입니다", response = CommentResponseDto.class),
        @ApiResponse(code = 403, message = "권한이 없습니다")
    })
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto content, @AuthenticationPrincipal OAuth2UserImpl user) {
        CommentResponseDto comment = boardService.updateComment(commentId, content, user);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공입니다", response = CommentResponseDto.class),
        @ApiResponse(code = 403, message = "권한이 없습니다")
    })
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal OAuth2UserImpl user) {
        Boolean result = boardService.deleteComment(commentId, user);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "대댓글 작성", notes = "댓글에 새로운 대댓글을 작성합니다.")
    @ApiResponses({
        @ApiResponse(code = 201, message = "성공입니다", response = CommentResponseDto.class),
        @ApiResponse(code = 403, message = "유효하지 않은 요청입니다")
    })
    @PostMapping("/{boardId}/comments/{parentId}")
    public ResponseEntity<?> createComment(@PathVariable Long boardId, @PathVariable Long parentId, @RequestBody CommentRequestDto content, @AuthenticationPrincipal OAuth2UserImpl user) {
        List<CommentResponseDto> comment = boardService.createComment(boardId, parentId, content, user);
        if (comment == null) {
            return new ResponseEntity<>("유효하지 않은 요청입니다.", HttpStatus.OK);
        }
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @ApiOperation(value = "좋아요 상태 변경", notes = "좋아요 추가/삭제 상태로 변경합니다.")
    @ApiResponses({
        @ApiResponse(code = 201, message = "성공입니다.")
    })
    @PostMapping("/likes/{boardId}")
    public ResponseEntity<HttpStatus> updateLike(@PathVariable Long boardId, @AuthenticationPrincipal OAuth2UserImpl user) {
        boardService.updateLike(boardId, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "게시글 찜하기", notes = "게시글을 찜 등록/해제 상태로 변경합니다.")
    @ApiResponses({
        @ApiResponse(code = 201, message = "성공입니다.")
    })
    @PostMapping("/dibs/{boardId}")
    public ResponseEntity<HttpStatus> updateDibs(@PathVariable Long boardId, @AuthenticationPrincipal OAuth2UserImpl user) {
        boardService.updateDibs(boardId, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "찜 목록 조회", notes = "커뮤니티 게시판 찜 목록을 조회합니다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공입니다", response = BoardResponseDto.class)
    })
    @GetMapping("/dibs")
    public ResponseEntity<?> getBoardDibsList(@ApiParam(name = "page", value = "조회할 페이지", defaultValue = "0") @RequestParam int page, @AuthenticationPrincipal OAuth2UserImpl user) {
        Page<BoardListResponseDto> dibsList = boardService.getBoardDibsList(page, user);
        return new ResponseEntity<>(dibsList, HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 검색", notes = "입력한 단어가 제목, 내용, 작성자 중 선택한 조건에 들어있는 글을 검색합니다")
    @GetMapping("/search")
    public ResponseEntity<?> searchBoardByTitle(
        @ApiParam(name = "page", value = "조회할 페이지", defaultValue = "0") @RequestParam int page,
        @ApiParam(name = "title", value = "제목 검색할 단어") @RequestParam(required = false) String title,
        @ApiParam(name = "content", value = "내용 검색할 단어") @RequestParam(required = false) String content,
        @ApiParam(name = "nickname", value = "닉네임 검색할 단어") @RequestParam(required = false) String nickname) {
        Page<BoardListResponseDto> searchList = boardService.searchBoard(page, title, content, nickname);
        return new ResponseEntity<>(searchList, HttpStatus.OK);
    }


}
