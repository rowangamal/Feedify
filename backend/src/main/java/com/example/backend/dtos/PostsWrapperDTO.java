package com.example.backend.dtos;

import com.example.backend.entities.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PostsWrapperDTO {
    private List<PostsResponseDTO> postResponses;
    private List<Post> posts;
    private int totalPages;
}
