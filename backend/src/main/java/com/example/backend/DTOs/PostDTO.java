package com.example.backend.DTOs;

import com.example.backend.entities.PostType;

import java.util.List;

public class PostDTO {
    public String content ;
    public List<PostType> types  ;
    public String imageURL ;
    public PostDTO(){}
}
