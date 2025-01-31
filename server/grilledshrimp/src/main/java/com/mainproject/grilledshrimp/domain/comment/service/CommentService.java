package com.mainproject.grilledshrimp.domain.comment.service;

import com.mainproject.grilledshrimp.domain.comment.dto.CommentDTO;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentResponseDto;
import com.mainproject.grilledshrimp.domain.comment.entity.Comment;
import com.mainproject.grilledshrimp.domain.comment.repository.CommentRepository;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.repository.PostsRepository;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import com.mainproject.grilledshrimp.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostsRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addComment(Long userId, Long postId, String commentText) {
        // 1. 사용자 엔티티 조회
        Users user = userRepository.findByuserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 2. 게시물 엔티티 조회
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        // 3. 댓글 엔티티 생성 및 필드 설정
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setCommentText(commentText);
        LocalDateTime currentTime = LocalDateTime.now();
        comment.setCreated_at(currentTime);
        comment.setModified_at(currentTime);

        commentRepository.save(comment);
    }
    @Transactional
    public void updateComment(Long commentId, String updatedText) {
        // commentId로 댓글을 조회하여 업데이트
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        comment.setCommentText(updatedText);
        comment.setModifiedAt(LocalDateTime.now());

        // 수정된 댓글을 저장
        commentRepository.save(comment);
    }
    @Transactional
    public void deleteComment(Long commentId) {
        // commentId로 댓글을 조회하여 삭제
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        commentRepository.delete(comment);
    }

    public Page<CommentDTO> getCommentsByPostId(Long postId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByPosts_PostId(postId, pageable);
        return comments.map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setUserId(comment.getUser().getUserId());
            commentDTO.setCommentText(comment.getCommentText());
            commentDTO.setCommentId(comment.getCommentId());
            return commentDTO;
        });
    }
}