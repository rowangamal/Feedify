package com.example.backend.entities;

import com.example.backend.enums.TableColNames;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(
        name = TableColNames.COMMENT_TABLE,
        indexes = {
                @Index(name = "idx_comment_user_id", columnList = TableColNames.COMMENT_USER_ID),
                @Index(name = "idx_comment_post_id", columnList = TableColNames.COMMENT_POST_ID)
        }
)
public class Comment extends BaseEntity {
    @Column(name = TableColNames.COMMENT_CONTENT, nullable = false)
    private String content;

    @Column(name = TableColNames.COMMENT_DATE)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = TableColNames.COMMENT_USER_ID, nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = TableColNames.COMMENT_POST_ID, nullable = false)
    private Post post;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

}
