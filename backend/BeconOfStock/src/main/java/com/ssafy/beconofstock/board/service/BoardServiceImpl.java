package com.ssafy.beconofstock.board.service;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.board.dto.BoardRequestDto;
import com.ssafy.beconofstock.board.dto.BoardResponseDto;
import com.ssafy.beconofstock.board.dto.CommentRequestDto;
import com.ssafy.beconofstock.board.dto.CommentResponseDto;
import com.ssafy.beconofstock.board.entity.Board;
import com.ssafy.beconofstock.board.entity.Comment;
import com.ssafy.beconofstock.board.entity.CommentRel;
import com.ssafy.beconofstock.board.repository.BoardRepository;
import com.ssafy.beconofstock.board.repository.CommentRelRepository;
import com.ssafy.beconofstock.board.repository.CommentRepository;
import com.ssafy.beconofstock.member.entity.Member;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentRelRepository commentRelRepository;

    public BoardResponseDto createBoard(Member member, BoardRequestDto board) {

//        Strategy strategy = strategyRepository.findByID();
        Board newBoard = Board.builder()
            .title(board.getTitle())
            .content(board.getContent())
//                .strategy(strategy)
            .member(member)
            .hit(0L)
            .likeNum(0L)
            .commentNum(0L)
            .build();

        return new BoardResponseDto(boardRepository.save(newBoard));
    }

    public List<BoardResponseDto> getBoardList() {

        List<Board> boardList = boardRepository.findAll();

        return boardList.stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }

    public BoardResponseDto getBoardDetail(Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        board.setHit(board.getHit() + 1);               // 조회수 업데이트
        return new BoardResponseDto(boardRepository.save(board));
    }

    // 글 삭제
    public Boolean deleteBoard(OAuth2UserImpl user, Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board.getMember().getId().equals(user.getMember().getId())) {
            boardRepository.deleteById(boardId);
            return true;
        }
        return false;
    }

    // 글 수정
    public BoardResponseDto updateBoard(OAuth2UserImpl user, BoardRequestDto updateBoard,
        Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);

        if (!board.getMember().getId().equals(user.getMember().getId())) {
            return null;
        }

        // 수정
        if (updateBoard.getTitle() != null) {
            board.setTitle(updateBoard.getTitle());
        }
        if (updateBoard.getContent() != null) {
            board.setContent(updateBoard.getContent());
        }
//        if (updateBoard.getStrategyId() != null) {
//            board.setStrategy(strategyRepository.findById(updateBoard.getStrategyId()));
//        }

        return new BoardResponseDto(boardRepository.save(board));
    }

    // 댓글 목록 전체 조회
    public List<CommentResponseDto> getCommentList(Long boardId) {
        List<Comment> commentList= commentRepository.findAllByBoardId(boardId);
        return commentList.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    // 단일 댓글 조회
    public CommentResponseDto getComment(Long commentId) {
        return new CommentResponseDto(commentRepository.findById(commentId).orElse(null));
    }

    // 댓글 생성
    public CommentResponseDto createComment(Long boardId, CommentRequestDto content, OAuth2UserImpl user) {
        Board board = boardRepository.findById(boardId).orElse(null);
        Long commentNum = board.getCommentNum() + 1;
        board.setCommentNum(commentNum);
        boardRepository.save(board);

        Comment comment = Comment.builder()
            .boardId(boardId)
            .content(content.getContent())
            .member(user.getMember())
            .likeNum(0L)
            .commentNum(0L)
            .depth(0)
            .build();

        return new CommentResponseDto(commentRepository.save(comment));
    }

    // 댓글 수정
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto content, OAuth2UserImpl user) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (!comment.getMember().getId().equals(user.getMember().getId())) {
            return null;
        }
        comment.setContent(content.getContent());
        return new CommentResponseDto(commentRepository.save(comment));
    }

    // 댓글 삭제
    public Boolean deleteComment(Long commentId, OAuth2UserImpl user) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        Board board = boardRepository.findById(comment.getBoardId()).orElse(null);
        Long commentNum = board.getCommentNum() - 1;
        board.setCommentNum(commentNum);
        boardRepository.save(board);

        if (!comment.getMember().getId().equals(user.getMember().getId())) {
            return false;
        }
        commentRepository.delete(comment);
        return true;
    }

    // 대댓글 작성
    public CommentResponseDto createComment(Long boardId, Long parentId, CommentRequestDto content, OAuth2UserImpl user) {
        Comment comment = Comment.builder()
            .boardId(boardId)
            .content(content.getContent())
            .member(user.getMember())
            .likeNum(0L)
            .commentNum(0L)
            .depth(1)
            .build();

        Comment child = commentRepository.save(comment);
        Comment parent = commentRepository.findById(parentId).orElse(null);
        Long commentNum = parent.getCommentNum() + 1;
        parent.setCommentNum(commentNum);

        CommentRel commentRel = CommentRel.builder()
            .parent(commentRepository.save(parent))
            .child(child)
            .build();

        return new CommentResponseDto(child);
    }
}
