package com.example.backend.Feed.Criterias;

import com.example.backend.entities.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserProfileCriteria implements ICriteria{
    private final long userId;

    public UserProfileCriteria(long userId) {
        this.userId = userId;
    }

    @Override
    public Predicate meetCriteria(CriteriaBuilder criteriaBuilder, Root<Post> root) {
        return criteriaBuilder.equal(root.get("user").get("id"), this.userId);
    }
}
