package com.example.backend.controllers;

import com.example.backend.dtos.FeedDTO;
import com.example.backend.dtos.PostsWrapperDTO;
import com.example.backend.dtos.UserInfoDTO;
import com.example.backend.services.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userProfile")
@CrossOrigin
public class FeedController {
    @Autowired
    private FeedService feedService;

    @PostMapping("/profileFeed")
    public ResponseEntity<PostsWrapperDTO> getProfileFeed(@RequestBody FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getPersonalProfileFeed(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/followingFeed")
    public ResponseEntity<PostsWrapperDTO> getFollowingFeed(@RequestBody FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getFollowingFeed(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/topicsFeed")
    public ResponseEntity<PostsWrapperDTO> getTopicsFeed(@RequestBody FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getTopicsFeed(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/visitedProfileFeed")
    public ResponseEntity<PostsWrapperDTO> getVisitedProfileFeed(@RequestBody FeedDTO feedDTO) {
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

    @PostMapping("/totalPages")
    public ResponseEntity<Integer> getTotalPages(@RequestBody FeedDTO feedDTO) {
        try {
            return ResponseEntity.ok(feedService.getTotalPages(feedDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
