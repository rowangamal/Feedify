package com.example.backend.services;

import com.example.backend.dtos.FeedDTO;
import com.example.backend.Feed.FeedFactory;
import com.example.backend.entities.Post;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FeedService implements IService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    public List<Post> getProfileFeed(FeedDTO feedDTO) {
        feedDTO.setUserId(userService.getUserId());
        return Objects.requireNonNull(FeedFactory.getFeed("UserProfile"))
                .filter(feedDTO.getTopics(), feedDTO.getUserId(), entityManager);
    }

    public List<Post> getFollowingFeed(FeedDTO feedDTO) {
        feedDTO.setUserId(userService.getUserId());
        return Objects.requireNonNull(FeedFactory.getFeed("Following"))
                .filter(feedDTO.getTopics(), feedDTO.getUserId(), entityManager);
    }

    public List<Post> getTopicsFeed(FeedDTO feedDTO) {
        feedDTO.setUserId(userService.getUserId());
        return Objects.requireNonNull(FeedFactory.getFeed("Topics"))
                .filter(feedDTO.getTopics(), feedDTO.getUserId(), entityManager);
    }
}
