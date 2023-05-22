package com.ssafy.beconofstock.board.repository;

import com.ssafy.beconofstock.board.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardId(Long boardId);
    List<Comment> findAllByBoardIdAndDepthEquals(Long boardId, int depth);

}
