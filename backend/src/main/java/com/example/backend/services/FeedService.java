package com.example.backend.services;

import com.example.backend.enums.CombinationMode;
import com.example.backend.feeds.Criterias.UserProfileCriteria;
import com.example.backend.feeds.Criterias.TopicCriteria;
import com.example.backend.feeds.FeedBuilder;
import com.example.backend.postInteractions.Post;
import com.example.backend.repositories.FeedRepository;

import java.util.List;

public class FeedService implements Service {
    private final FeedRepository feedRepository;

    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    public List<Post> getFilteredFeedByTopic(List<String> topics, CombinationMode combinationMode) {
        FeedBuilder feedBuilder = new FeedBuilder();
        feedBuilder.setCombinationMode(combinationMode);
        if (topics != null) {
            topics.forEach(type -> feedBuilder.addCriteria(new TopicCriteria(type)));
        }
        return feedRepository.findAll(feedBuilder.build());
    }

    public List<Post> getUserProfileFeed(int userId) {
        FeedBuilder feedBuilder = new FeedBuilder();
        feedBuilder.addCriteria(new UserProfileCriteria(userId));
        return feedRepository.findAll(feedBuilder.build());
    }

    public List<Post> getFollowingFeed(int userId) {
        FeedBuilder feedBuilder = new FeedBuilder();
        feedBuilder.addCriteria(new UserProfileCriteria(userId));
        return feedRepository.findAll(feedBuilder.build());
    }
}
