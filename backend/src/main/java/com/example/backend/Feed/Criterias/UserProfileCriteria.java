package com.example.backend.Feed.Criterias;

import com.example.backend.entities.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserProfileCriteria implements ICriteria{
    private int userId;

    public UserProfileCriteria(int userId) {
        this.userId = userId;
    }

    @Override
    public Predicate meetCriteria(CriteriaBuilder cb, Root<Post> root) {
        return cb.equal(root.get("user").get("id"), this.userId);
    }
}
