package com.mainproject.grilledshrimp.domain.comment.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mainproject.grilledshrimp.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    static Page<Comment> findByPosts_postid(Long postId, Pageable pageable) {
        return null;
    }
}