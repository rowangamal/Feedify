package com.example.backend.services;

import com.example.backend.DTOs.FeedDTO;
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
    private EntityManager em;

    public List<Post> getProfileFeed(FeedDTO feedDTO) {
        return Objects.requireNonNull(FeedFactory.getFeed("UserProfile"))
                .filter(feedDTO.getTopics(), feedDTO.getUserId(), em);
    }

    public List<Post> getFollowingFeed(FeedDTO feedDTO) {
        return Objects.requireNonNull(FeedFactory.getFeed("Following"))
                .filter(feedDTO.getTopics(), feedDTO.getUserId(), em);
    }

    public List<Post> getTopicsFeed(FeedDTO feedDTO) {
        return Objects.requireNonNull(FeedFactory.getFeed("Topics"))
                .filter(feedDTO.getTopics(), feedDTO.getUserId(), em);
    }
}
