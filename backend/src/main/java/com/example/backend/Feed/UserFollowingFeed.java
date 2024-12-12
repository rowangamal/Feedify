package com.example.backend.Feed;

import com.example.backend.entities.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class UserFollowingFeed implements IFeed{
    @PersistenceContext
    private EntityManager entityManger;
    @Override
    public List<Post> filter(List<String> topics, long userId, EntityManager entityManger) {
        return List.of();
    }
}
