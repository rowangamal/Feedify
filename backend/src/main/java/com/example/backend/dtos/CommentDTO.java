package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentDTO {
    private long id;
    private String content;
    private long postId;
    private long userId;
    private Timestamp createdAt;
}
