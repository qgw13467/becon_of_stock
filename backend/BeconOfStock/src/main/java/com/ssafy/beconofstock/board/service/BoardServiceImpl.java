package com.ssafy.beconofstock.board.service;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.board.dto.BoardRequestDto;
import com.ssafy.beconofstock.board.dto.BoardResponseDto;
import com.ssafy.beconofstock.board.entity.Board;
import com.ssafy.beconofstock.board.repository.BoardRepository;
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

    public BoardResponseDto createBoard(Member member, BoardRequestDto board) {

//        Strategy strategy = strategyRepository.findByID();
        Board newBoard = Board.builder()
            .title(board.getTitle())
            .content(board.getContent())
//                .strategy(strategy)
            .member(member)
            .hit(0L)
            .likeNum(0L)
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
}
