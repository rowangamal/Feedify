package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentsDTO {
    private long id;
    private String content;
    private String username;
    private String pictureURL;
    private String createdAt;
}
