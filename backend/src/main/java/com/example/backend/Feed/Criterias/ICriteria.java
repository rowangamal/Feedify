package com.example.backend.Feed.Criterias;

import com.example.backend.entities.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface ICriteria {
    public Predicate meetCriteria(CriteriaBuilder criteriaBuilder, Root<Post> root);
}
