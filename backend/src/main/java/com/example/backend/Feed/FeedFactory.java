package com.example.backend.Feed;

public class FeedFactory {
    public static IFeed getFeed(String feedType){
        return switch (feedType) {
            case "UserProfile" -> new UserProfileFeed();
            case "Following" -> new UserFollowingFeed();
            case "Topics" -> new UserTopicsFeed();
            default -> null;
        };
    }
}
