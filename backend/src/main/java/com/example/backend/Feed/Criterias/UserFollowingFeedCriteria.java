package com.example.backend.Feed.Criterias;

import com.example.backend.entities.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserFollowingFeedCriteria implements ICriteria{
    private int userId;

    public UserFollowingFeedCriteria(int userId) {
        this.userId = userId;
    }
    @Override
    public Predicate meetCriteria(CriteriaBuilder criteriaBuilder, Root<Post> root) {
        return null;
    }
}
