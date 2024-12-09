package com.example.backend.controllers;

import com.example.backend.DTOs.PostDTO;
import com.example.backend.entities.PostType;
import com.example.backend.exceptions.PostOutOfLimitException;
import com.example.backend.exceptions.PostWithNoType;
import com.example.backend.exceptions.PostWithZeroContent;
import com.example.backend.services.PostService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
//    ,@RequestParam MultipartFile file
    public ResponseEntity<String> signup(@RequestBody PostDTO postDTO) {
        try {
            postService.createPost(postDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
        } catch (PostOutOfLimitException | PostWithNoType | PostWithZeroContent | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating post");
        }

    }
}
