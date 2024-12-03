package com.example.backend.controllers;


import com.example.backend.DTOs.FeedDTO;
import com.example.backend.Feed.FeedFactory;
import com.example.backend.entities.Post;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userProfile")
@CrossOrigin
public class FeedController {
    @Autowired
    private FeedFactory feedFactory;

    @Autowired
    private EntityManager em;

    @PostMapping("/profileFeed")
    public List<Post> getProfileFeed(@RequestBody FeedDTO feedDTO) {
        return feedFactory.getFeed("UserProfile").filter(feedDTO.getTopics(), feedDTO.getUserId(), em);
    }

    @PostMapping("/followingFeed")
    public List<Post> getFollowingFeed(@RequestBody FeedDTO feedDTO) {
        return feedFactory.getFeed("Following").filter(feedDTO.getTopics(), feedDTO.getUserId(), em);
    }

    @PostMapping("/topicsFeed")
    public List<Post> getTopicsFeed(@RequestBody FeedDTO feedDTO) {
        return feedFactory.getFeed("Topics").filter(feedDTO.getTopics(), feedDTO.getUserId(), em);
    }
}
