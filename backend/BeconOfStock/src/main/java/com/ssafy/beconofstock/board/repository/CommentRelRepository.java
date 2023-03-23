package com.ssafy.beconofstock.board.repository;

import com.ssafy.beconofstock.board.entity.CommentRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRelRepository extends JpaRepository<CommentRel, Long> {


}
