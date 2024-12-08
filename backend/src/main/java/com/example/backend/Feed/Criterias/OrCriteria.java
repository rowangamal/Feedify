package com.example.backend.Feed.Criterias;

import com.example.backend.entities.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class OrCriteria implements ICriteria{

    private final ICriteria criteria1;
    private final ICriteria criteria2;

    public OrCriteria(ICriteria criteria1, ICriteria criteria2) {
        this.criteria1 = criteria1;
        this.criteria2 = criteria2;
    }
    @Override
    public Predicate meetCriteria(CriteriaBuilder criteriaBuilder, Root<Post> root) {
        Predicate predicate1 = criteria1.meetCriteria(criteriaBuilder, root);
        Predicate predicate2 = criteria2.meetCriteria(criteriaBuilder, root);
        return criteriaBuilder.or(predicate1, predicate2);
    }
}
