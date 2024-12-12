package com.example.backend.Feed;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeedFactoryTest {

    @Test
    void getFeedUserProfile() {
        String feedType = "UserProfile";
        IFeed feed = FeedFactory.getFeed(feedType);
        assertInstanceOf(UserProfileFeed.class, feed);
    }

    @Test
    void getFeedUserFollowing() {
        String feedType = "Following";
        IFeed feed = FeedFactory.getFeed(feedType);
        assertInstanceOf(UserFollowingFeed.class, feed);
    }

    @Test
    void getFeedUserTopics() {
        String feedType = "Topics";
        IFeed feed = FeedFactory.getFeed(feedType);
        assertInstanceOf(UserTopicsFeed.class, feed);
    }

    @Test
    void getFeedNull() {
        String feedType = "Invalid";
        IFeed feed = FeedFactory.getFeed(feedType);
        assertNull(feed);
    }
}