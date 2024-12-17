package com.example.backend.controllers;

import com.example.backend.dtos.FeedDTO;
import com.example.backend.dtos.PostsResponseDTO;
import com.example.backend.entities.Post;
import com.example.backend.services.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/userProfile")
@CrossOrigin
public class FeedController {
    @Autowired
    private FeedService feedService;

    @PostMapping("/profileFeed")
    public ResponseEntity<List<Post>> getProfileFeed(@RequestBody FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getProfileFeed(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/followingFeed")
    public ResponseEntity<List<PostsResponseDTO>> getFollowingFeed(@RequestBody FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getFollowingFeed(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/topicsFeed")
    public ResponseEntity<List<PostsResponseDTO>> getTopicsFeed(@RequestBody FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getTopicsFeed(feedDTO));
        } catch (Exception e) {
            log.error("e: ", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
