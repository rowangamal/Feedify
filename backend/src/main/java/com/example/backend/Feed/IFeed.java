package com.example.backend.Feed;

import com.example.backend.entities.Post;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface IFeed {
    public List<Post> filter(List<String> topics, int userId, EntityManager em);
}
