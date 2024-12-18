package com.example.backend.repositories;

import com.example.backend.entities.PostType;
import com.example.backend.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @PersistenceContext
    private EntityManager entityManager;

    private User user1;
    private User user2;
    private User user3;

    private PostType sport;
    private PostType Technology;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        user1.setPassword("password");
        entityManager.persist(user1);

        user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        user2.setPassword("password");
        entityManager.persist(user2);

        user3 = new User();
        user3.setUsername("user3");
        user3.setEmail("user3@example.com");
        user3.setPassword("password");
        entityManager.persist(user3);

        sport = new PostType("Sport");
        entityManager.persist(sport);

        Technology = new PostType("Technology");
        entityManager.persist(Technology);

        checkFollowingList(user1);
        checkFollowingList(user2);
        checkFollowingList(user3);

        user1.getFollowing().add(user2);
        user2.getFollowing().add(user3);

        checkPostTypeList(user1);
        checkPostTypeList(user2);
        checkPostTypeList(user3);

        user1.getPostTypes().add(sport);
        user2.getPostTypes().add(sport);
        user2.getPostTypes().add(Technology);
        user3.getPostTypes().add(Technology);

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(user3);

        entityManager.flush();
    }

    @Test
    void testGetFollowedUsersOfUser() {
        List<User> followedUsers = userRepo.getFollowedUsersOfUser(user1.getId());
        List<User> followedUsers2 = userRepo.getFollowedUsersOfUser(user2.getId());
        List<User> followedUsers3 = userRepo.getFollowedUsersOfUser(user3.getId());

        assertThat(followedUsers).hasSize(1);
        assertThat(followedUsers.getFirst().getUsername()).isEqualTo("user2");

        assertThat(followedUsers2).hasSize(1);
        assertThat(followedUsers2.getFirst().getUsername()).isEqualTo("user3");

        assertThat(followedUsers3).isEmpty();
    }

    @Test
    void testGetUserInterests() {
        List<String> interestsUser1 = userRepo.getUserInterests(user1.getId());
        List<String> interestsUser2 = userRepo.getUserInterests(user2.getId());
        List<String> interestsUser3 = userRepo.getUserInterests(user3.getId());

        assertThat(interestsUser1).contains("Sport");
        assertThat(interestsUser2).contains("Sport", "Technology");
        assertThat(interestsUser3).contains("Technology");
    }

    @AfterEach
    void tearDown() {
        Query deleteFollowings = entityManager.createQuery("DELETE FROM User u WHERE u IN :users");
        deleteFollowings.setParameter("users", List.of(user1, user2, user3));
        deleteFollowings.executeUpdate();

        Query deletePostTypes = entityManager.createQuery("DELETE FROM PostType pt WHERE pt IN :postTypes");
        deletePostTypes.setParameter("postTypes", List.of(sport, Technology));
        deletePostTypes.executeUpdate();
    }

    private void checkPostTypeList(User user) {
        if(user.getPostTypes() == null) {
            user.setPostTypes(new ArrayList<>());
        }
    }

    private void checkFollowingList(User user) {
        if(user.getFollowing() == null) {
            user.setFollowing(new ArrayList<>());
        }
    }
}
