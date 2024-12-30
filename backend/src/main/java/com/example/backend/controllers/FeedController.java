package com.example.backend.controllers;

import com.example.backend.dtos.FeedDTO;
import com.example.backend.dtos.PostsResponseDTO;
import com.example.backend.dtos.UserInfoDTO;
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
            return ResponseEntity.ok(feedService.getPersonalProfileFeed(feedDTO));
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

    @PostMapping("/visitedProfileFeed")
    public ResponseEntity<List<PostsResponseDTO>> getVisitedProfileFeed(@RequestBody FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getVisitedProfileFeed(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/getUser")
    public ResponseEntity<UserInfoDTO> getUser(@RequestBody FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getUser(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
