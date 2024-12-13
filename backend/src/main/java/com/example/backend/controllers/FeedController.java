package com.example.backend.controllers;

import com.example.backend.dtos.FeedDTO;
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

    @PostMapping("/profileFeed")
    public ResponseEntity<List<Post>> getProfileFeed(@RequestBody FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getProfileFeed(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/followingFeed")
    public ResponseEntity<List<Post>> getFollowingFeed(@RequestBody FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getFollowingFeed(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/topicsFeed")
    public ResponseEntity<List<Post>> getTopicsFeed(@RequestBody FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getTopicsFeed(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
