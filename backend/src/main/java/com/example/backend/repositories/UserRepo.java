package com.example.backend.repositories;

import com.example.backend.entities.Post;
import com.example.backend.entities.PostType;
import com.example.backend.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepo {
    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getUsersByPosts(List<Post> posts) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<Post> postRoot = criteriaQuery.from(Post.class);
        Join<Post, User> userJoin = postRoot.join("user");
        criteriaQuery.select(userJoin)
                .where(postRoot.in(posts));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<User> getFollowedUsersOfUser(Long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Join<User, User> followedUsers = userRoot.join("following");
        criteriaQuery.select(followedUsers)
                .where(criteriaBuilder.equal(userRoot.get("id"), userId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<String> getUserInterests(Long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Join<User, PostType> postTypeJoin = userRoot.join("postTypes");
        criteriaQuery.select(postTypeJoin.get("name"))
                .where(criteriaBuilder.equal(userRoot.get("id"), userId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
