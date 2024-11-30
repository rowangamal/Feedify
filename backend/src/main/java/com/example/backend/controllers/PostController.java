package com.example.backend.controllers;

import com.example.backend.DTOs.PostDTO;
import com.example.backend.entities.PostType;
import com.example.backend.exceptions.PostOutOfLimitException;
import com.example.backend.services.PostService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/post")
@CrossOrigin
public class PostController implements Controller {
    @Autowired
    private PostService postService;
    @PostMapping("/createPost")
    public ResponseEntity<String> signup(@RequestBody PostDTO postDTO) {
        try {
            postService.createPost(postDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Post created successfully");
        } catch (PostOutOfLimitException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating post");
        }
    }
}
