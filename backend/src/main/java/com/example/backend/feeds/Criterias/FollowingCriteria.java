package com.example.backend.feeds.Criterias;

import com.example.backend.postInteractions.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class FollowingCriteria implements Criteria<Post> {
    private final int userID;

    public FollowingCriteria(int userId) {
        this.userID = userId;
    }

    @Override
    public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
