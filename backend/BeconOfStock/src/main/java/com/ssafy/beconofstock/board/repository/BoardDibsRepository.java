package com.ssafy.beconofstock.board.repository;

import com.ssafy.beconofstock.board.entity.Board;
import com.ssafy.beconofstock.board.entity.BoardDibs;
import com.ssafy.beconofstock.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDibsRepository extends JpaRepository<BoardDibs, Long> {
    Boolean existsByBoardAndMember(Board board, Member member);
    void deleteByBoardAndMember(Board board, Member member);

}
