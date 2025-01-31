package com.mainproject.grilledshrimp.domain.comment.entity;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.recommendComment.entity.RecommendComment;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts posts;

    @Column(nullable = false)
    private String comment_text;

    @Column(nullable = false)
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modified_at;

    @OneToMany(mappedBy = "comment")
    private List<RecommendComment> recommendCommentList = new ArrayList<>();

    public void setPost(Posts post) {
        this.posts = post;
    }
    public void setUser(Users user) {
        this.users = user;
    }

    public void setCommentText(String commentText) {
        this.comment_text =commentText;
    }

    public void setModifiedAt(LocalDateTime now) {
        this.modified_at = now;
    }


    public Users getUser() {
        return users;
    }

    public String getCommentText() {
        return comment_text;
    }

    public Users getCommentId() {
        return users;
    }
}
