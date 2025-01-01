package com.example.backend.entities;

import com.example.backend.enums.TableColNames;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(
        name = TableColNames.POST_TABLE,
        indexes = @Index(name = "idx_post_user_id", columnList = TableColNames.POST_USER_ID)
)
public class Post extends BaseEntity {
    @Column(name = TableColNames.POST_CONTENT, nullable = false, length = 1000)
    private String content;

    @Column(name = TableColNames.POST_LIKES_COUNT)
    private int likesCount;

    @Column(name = TableColNames.POST_COMMENTS_COUNT)
    private int commentsCount;

    @Column(name = TableColNames.POST_REPOST_COUNT)
    private int repostsCount;

    @Column(name = TableColNames.POST_IMAGE)
    private String image;

    @Column(name = TableColNames.POST_DATE)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = TableColNames.POST_USER_ID)
    @JsonBackReference
    private User user;

    @ManyToMany
    @JoinTable(
            name = TableColNames.OF_TYPE_TABLE,
            joinColumns = @JoinColumn(name = TableColNames.OF_TOPIC_POST_ID),
            inverseJoinColumns = @JoinColumn(name = TableColNames.OF_TOPIC_TOPIC_ID)

    )
    private List<PostType> postTypes;


    @OneToMany(mappedBy = "post")
    @JsonBackReference
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    @JsonBackReference
    private List<Like> likes;

    @OneToMany(mappedBy = "post")
    @JsonBackReference
    private List<Repost> reposts;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ReportPost> reports;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.likesCount = 0;
        this.commentsCount = 0;
        this.repostsCount = 0;
    }

}
