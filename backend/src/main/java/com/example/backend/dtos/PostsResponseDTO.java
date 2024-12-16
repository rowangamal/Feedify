package com.example.backend.dtos;

import com.example.backend.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostsResponseDTO {
    private Long userId;
    private String username;
    private String userPicture;

    private Long postId;
    private String content;
    private String postImage;
    private int likesCount;
    private int commentsCount;
    private int repostsCount;
    private Timestamp createdAt;
}
