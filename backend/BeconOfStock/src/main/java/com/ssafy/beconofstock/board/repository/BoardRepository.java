package com.ssafy.beconofstock.board.repository;

import com.ssafy.beconofstock.board.entity.Board;
import com.ssafy.beconofstock.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAll(Pageable pageable);

    @Query(value = "select board "
        + "FROM Board board "
        + "INNER JOIN BoardDibs dibs ON dibs.board = board "
        + "WHERE dibs.member = :paramMember")
    Page<Board> findBoardsByDibs(@Param(value = "paramMember") Member member, Pageable pageable);

}
