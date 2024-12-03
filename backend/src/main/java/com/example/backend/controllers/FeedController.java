package com.example.backend.controllers;


import com.example.backend.DTOs.FeedDTO;
import com.example.backend.Feed.FeedFactory;
import com.example.backend.entities.Post;
import com.example.backend.services.FeedService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userProfile")
@CrossOrigin
public class FeedController {
    @Autowired
    private FeedService feedService;

    @PostMapping("/profileFeed")
    public List<Post> getProfileFeed(@RequestBody FeedDTO feedDTO) {
        return feedService.getProfileFeed(feedDTO);
    }

    @PostMapping("/followingFeed")
    public List<Post> getFollowingFeed(@RequestBody FeedDTO feedDTO) {
        return feedService.getFollowingFeed(feedDTO);
    }

    @PostMapping("/topicsFeed")
    public List<Post> getTopicsFeed(@RequestBody FeedDTO feedDTO) {
        return feedService.getTopicsFeed(feedDTO);
    }
}
