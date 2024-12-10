package com.example.backend.Feed;

import com.example.backend.Feed.Criterias.UserProfileCriteria;
import com.example.backend.entities.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class UserProfileFeed implements IFeed{

    @Override
    public List<Post> filter(List<String> topics, int userId, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
        UserProfileCriteria userProfileCriteria = new UserProfileCriteria(userId);
        Root<Post> postRoot = criteriaQuery.from(Post.class);
        Predicate predicate = userProfileCriteria.meetCriteria(criteriaBuilder, postRoot);
        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
