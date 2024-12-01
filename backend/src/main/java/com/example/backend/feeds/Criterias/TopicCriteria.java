package com.example.backend.feeds.Criterias;

import com.example.backend.feeds.Feed;
import com.example.backend.postInteractions.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TopicCriteria implements Criteria<Post> {
    private final String topic;

    public TopicCriteria(String topic) {
        this.topic = topic;
    }

    @Override
    public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
