package com.example.backend.controllers;

import com.example.backend.dtos.FeedDTO;
import com.example.backend.dtos.PostsResponseDTO;
import com.example.backend.entities.Post;
import com.example.backend.services.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userProfile")
@CrossOrigin
public class FeedController {
    @Autowired
    private FeedService feedService;

    @GetMapping("/profileFeed")
    public ResponseEntity<List<Post>> getProfileFeed(FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getProfileFeed(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/followingFeed")
    public ResponseEntity<List<PostsResponseDTO>> getFollowingFeed(FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getFollowingFeed(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/topicsFeed")
    public ResponseEntity<List<PostsResponseDTO>> getTopicsFeed(FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getTopicsFeed(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
