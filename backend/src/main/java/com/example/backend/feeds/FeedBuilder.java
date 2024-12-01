package com.example.backend.feeds;

import com.example.backend.enums.CombinationMode;
import com.example.backend.feeds.Criterias.Criteria;
import com.example.backend.postInteractions.Post;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FeedBuilder {
    private final List<Criteria<Post>> criteria = new ArrayList<>();
    private CombinationMode combinationMode = CombinationMode.AND;

    public FeedBuilder addCriteria(Criteria<Post> criteria) {
        this.criteria.add(criteria);
        return this;
    }

    public FeedBuilder setCombinationMode(CombinationMode combinationMode) {
        this.combinationMode = combinationMode;
        return this;
    }

    public Specification<Post> build() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Criteria<Post> criteria : this.criteria) {
                predicates.add(criteria.toPredicate(root, query, criteriaBuilder));
            }
            if (combinationMode == CombinationMode.OR) {
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            } else {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
