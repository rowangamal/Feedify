package com.example.backend.feeds.Criterias;

import com.example.backend.postInteractions.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserProfileCriteria implements Criteria<Post> {
    final private int userID;

    public UserProfileCriteria(int userId) {
        this.userID = userId;
    }

    @Override
    public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("userID"), userID);
    }
}
