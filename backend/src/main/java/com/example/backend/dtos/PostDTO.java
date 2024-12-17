package com.example.backend.dtos;

import com.example.backend.entities.PostType;
import com.example.backend.entities.User;

import java.sql.Timestamp;
import java.util.List;

public class PostDTO {
    public String content ;
    public String username ;
    public Timestamp createdAt ;
    public List<PostType> types  ;
    public String imageURL ;
    public PostDTO(){}
}
