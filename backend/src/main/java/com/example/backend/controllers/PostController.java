package com.example.backend.controllers;

import com.example.backend.dtos.PostDTO;
import com.example.backend.entities.User;
import com.example.backend.exceptions.PostOutOfLimitException;
import com.example.backend.exceptions.PostWithNoType;
import com.example.backend.exceptions.PostWithZeroContent;
import com.example.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/post")
@CrossOrigin("*")
public class PostController implements Controller {
    @Autowired
    private PostService postService;
    @PostMapping("/createPost")
    public ResponseEntity<String> signup(@RequestBody PostDTO postDTO) {
        try {
            postService.createPost(postDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
        } catch (PostOutOfLimitException | PostWithNoType | PostWithZeroContent | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating post");
        }

    }
    @GetMapping("/{postID}")
    public ResponseEntity getPost(@PathVariable Long postID) {
        try {
            PostDTO postDTO = postService.getPost(postID);
            return ResponseEntity.ok(postDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
