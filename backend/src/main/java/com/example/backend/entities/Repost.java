package com.example.backend.entities;

import com.example.backend.enums.TableColNames;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(
        name = TableColNames.REPOST_TABLE,
        indexes = @Index(name = "idx_repost_user_id", columnList = TableColNames.REPOST_USER_ID)
)
public class Repost extends BaseEntity {
    @Column(name = TableColNames.REPOST_DATE)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = TableColNames.REPOST_USER_ID, nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = TableColNames.REPOST_POST_ID, nullable = false)
    private Post post;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
