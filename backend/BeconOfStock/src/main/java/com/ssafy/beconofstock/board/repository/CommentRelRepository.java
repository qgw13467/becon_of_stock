package com.ssafy.beconofstock.board.repository;

import com.ssafy.beconofstock.board.dto.CommentResponseDto;
import com.ssafy.beconofstock.board.entity.Comment;
import com.ssafy.beconofstock.board.entity.CommentRel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRelRepository extends JpaRepository<CommentRel, Long> {

    List<CommentRel> findAllByParent(Comment parent);
}
