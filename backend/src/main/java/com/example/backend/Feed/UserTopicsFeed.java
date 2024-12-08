package com.example.backend.Feed;

import com.example.backend.entities.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class UserTopicsFeed implements IFeed{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Post> filter(List<String> topics, int userId, EntityManager entityManager) {

        return List.of();
    }
}
