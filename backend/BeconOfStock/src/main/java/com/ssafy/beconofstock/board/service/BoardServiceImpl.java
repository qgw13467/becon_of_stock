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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentRelRepository commentRelRepository;

    @Transactional
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

    public Page<BoardResponseDto> getBoardList(int page, boolean direction, String property) {
        List<Sort.Order> sorts = new ArrayList<>();
        if (direction) {
            sorts.add(Sort.Order.asc(property));
        } else {
            sorts.add(Sort.Order.desc(property));
        }
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<Board> boardList = boardRepository.findAll(pageable);
        return boardList.map(BoardResponseDto::new);
    }

    public BoardResponseDto getBoardDetail(Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        board.setHit(board.getHit() + 1);               // 조회수 업데이트
        return new BoardResponseDto(boardRepository.save(board));
    }

    // 글 삭제
    @Transactional
    public Boolean deleteBoard(OAuth2UserImpl user, Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        List<Comment> commentList = commentRepository.findAllByBoardId(boardId);
        commentList.forEach(x -> deleteComment(x.getId(), user));
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

        return new BoardResponseDto(boardRepository.save(board));
    }

    // 댓글 목록 전체 조회
    public List<CommentResponseDto> getCommentList(Long boardId) {
        List<Comment> commentList= commentRepository.findAllByBoardIdAndDepthEquals(boardId, 0);
        return commentList.stream().map(x -> getComment(x.getId())).collect(Collectors.toList());
    }

    // 단일 댓글 조회
    public CommentResponseDto getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (0 < comment.getCommentNum()) {
            List<CommentRel> relList = commentRelRepository.findAllByParent(comment);
            List<Comment> childrenList = relList.stream().map(CommentRel::getChild).collect(Collectors.toList());
            List<CommentResponseDto> children = childrenList.stream().map(CommentResponseDto::new).collect(
                Collectors.toList());
            return new CommentResponseDto(comment, children);
        }
        return new CommentResponseDto(comment);
    }

    // 댓글 생성
    @Transactional
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
    @Transactional
    public Boolean deleteComment(Long commentId, OAuth2UserImpl user) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (!comment.getMember().getId().equals(user.getMember().getId())) {
            return false;
        }
        Board board = boardRepository.findById(comment.getBoardId()).orElse(null);
        int commentNum = 1;

        // 부모 댓글인 경우
        if (0 < comment.getCommentNum()) {
            List<CommentRel> children = commentRelRepository.findAllByParent(comment);
            commentNum += children.size();
            children.forEach(c -> commentRepository.delete(c.getChild())); // 자식 댓글 삭제
            commentRelRepository.deleteAllInBatch(children);
        }

        // 자식 댓글인 경우
        if (comment.getDepth() == 1) {
            Comment parent = commentRelRepository.findByChild(comment).getParent();
            commentRelRepository.deleteByChild(comment);
            parent.decreaseCommentNum(1);
            commentRepository.save(parent);
        }

        commentRepository.delete(comment);

        board.decreaseCommentNum(commentNum);
        boardRepository.save(board);

        return true;
    }

    // 대댓글 작성
    @Transactional
    public CommentResponseDto createComment(Long boardId, Long parentId, CommentRequestDto content, OAuth2UserImpl user) {
        Comment parent = commentRepository.findById(parentId).orElse(null);
        if ((parent.getDepth() == 1) || (!boardId.equals(parent.getBoardId()))) {
            return null;
        }

        Comment comment = Comment.builder()
            .boardId(boardId)
            .content(content.getContent())
            .member(user.getMember())
            .likeNum(0L)
            .commentNum(0L)
            .depth(1)
            .build();

        Comment child = commentRepository.save(comment);
        parent.increaseCommentNum(1);
        parent = commentRepository.save(parent);
        Board board = boardRepository.findById(boardId).orElse(null);
        board.increaseCommentNum(1);
        boardRepository.save(board);

        CommentRel commentRel = CommentRel.builder()
            .parent(parent)
            .child(child)
            .build();

        commentRelRepository.save(commentRel);

        return new CommentResponseDto(child);
    }
}
