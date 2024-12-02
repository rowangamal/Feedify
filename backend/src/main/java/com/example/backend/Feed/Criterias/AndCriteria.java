package com.example.backend.Feed.Criterias;

import com.example.backend.entities.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class AndCriteria implements ICriteria {

    private final ICriteria criteria;
    private final ICriteria otherCriteria;

    public AndCriteria(ICriteria criteria, ICriteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public Predicate meetCriteria(CriteriaBuilder cb, Root<Post> root) {
        Predicate criteriaPredicate = criteria.meetCriteria(cb, root);
        Predicate otherCriteriaPredicate = otherCriteria.meetCriteria(cb, root);
        return cb.and(criteriaPredicate, otherCriteriaPredicate);
    }
}
