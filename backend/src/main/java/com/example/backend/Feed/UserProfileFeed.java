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
    public List<Post> filter(List<String> topics, int userId, EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Post> cq = cb.createQuery(Post.class);
        UserProfileCriteria userProfileCriteria = new UserProfileCriteria(userId);
        Root<Post> postRoot = cq.from(Post.class);
        Predicate p = userProfileCriteria.meetCriteria(cb, postRoot);
        cq.where(p);
        return em.createQuery(cq).getResultList();
    }
}
