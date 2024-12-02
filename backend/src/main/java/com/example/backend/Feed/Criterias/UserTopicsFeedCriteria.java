package com.example.backend.Feed.Criterias;

import com.example.backend.entities.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class UserTopicsFeedCriteria implements ICriteria{
    private List<String> topics;

    public UserTopicsFeedCriteria(List<String> topics) {
        this.topics = topics;
    }
    @Override
    public Predicate meetCriteria(CriteriaBuilder cb, Root<Post> root) {
        return null;
    }
}
