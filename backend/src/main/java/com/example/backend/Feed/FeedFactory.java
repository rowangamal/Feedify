package com.example.backend.Feed;

import org.springframework.stereotype.Service;

@Service
public class FeedFactory {

    public FeedFactory() {
    }

    public IFeed getFeed(String feedType){
        return switch (feedType) {
            case "UserProfile" -> new UserProfileFeed();
            case "Following" -> new UserFollowingFeed();
            case "Topics" -> new UserTopicsFeed();
            default -> null;
        };

    }
}
