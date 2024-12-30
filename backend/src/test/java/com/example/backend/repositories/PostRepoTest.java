package com.example.backend.repositories;

import com.example.backend.dtos.PostsResponseDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.PostType;
import com.example.backend.entities.User;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostRepoTest {
    @Autowired
    private PostRepo postRepo;

    @PersistenceContext
    private EntityManager entityManager;

    private User user1;

    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setUsername("testuser");
        user1.setEmail("testuser@example.com");
        user1.setPassword("password123");
        entityManager.persist(user1);

        user2 = new User();
        user2.setUsername("testuser2");
        user2.setEmail("testuser2@example.com");
        user2.setPassword("password1234");
        entityManager.persist(user2);

        PostType postType1 = new PostType("Sport");
        PostType postType2 = new PostType("Technology");
        PostType postType3 = new PostType("Health");

        entityManager.persist(postType1);
        entityManager.persist(postType2);
        entityManager.persist(postType3);

        Post post1 = new Post();
        post1.setContent("Test post 1");
        post1.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        post1.setUser(user1);
        post1.setPostTypes(List.of(postType1, postType2));
        entityManager.persist(post1);

        Post post2 = new Post();
        post2.setContent("Test post 2");
        post2.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        post2.setUser(user2);
        post2.setPostTypes(List.of(postType1, postType3));
        entityManager.persist(post2);

        Post post3 = new Post();
        post3.setContent("Test post 3");
        post3.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        post3.setUser(user2);
        post3.setPostTypes(List.of(postType2, postType3));
        entityManager.persist(post3);

        Post post4 = new Post();
        post4.setContent("Test post 4");
        post4.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        post4.setUser(user1);
        post4.setPostTypes(List.of(postType1));
        entityManager.persist(post4);

        entityManager.flush();
    }

    @Test
    void testGetPostsByUser() {
        List<Post> posts = postRepo.getPostsByUser(user1.getId());

        assertThat(posts).isNotEmpty();
        assertThat(posts.get(0).getContent()).isEqualTo("Test post 4");
        assertThat(posts.get(1).getContent()).isEqualTo("Test post 1");
    }

    @Test
    void testGetPostsOfUsers() {
        List<PostsResponseDTO> posts = postRepo.getPostsOfUsers(List.of(user1, user2));

        assertThat(posts).hasSize(4);
        assertThat(posts.get(0).getContent()).isEqualTo("Test post 4");
        assertThat(posts.get(1).getContent()).isEqualTo("Test post 3");
        assertThat(posts.get(2).getContent()).isEqualTo("Test post 2");
        assertThat(posts.get(3).getContent()).isEqualTo("Test post 1");
    }

    @Test
    void testInsertAndRetrievePostType() {
        PostType sportType = entityManager.find(PostType.class, 1L);
        PostType trollType = entityManager.find(PostType.class, 2L);
        PostType politicsType = entityManager.find(PostType.class, 3L);

        assertThat(sportType).isNotNull();
        assertThat(sportType.getName()).isEqualTo("Sport");

        assertThat(trollType).isNotNull();
        assertThat(trollType.getName()).isEqualTo("Technology");

        assertThat(politicsType).isNotNull();
        assertThat(politicsType.getName()).isEqualTo("Health");
    }

    @AfterEach
    void tearDown() {
        Query deletePosts = entityManager.createQuery("DELETE FROM Post p WHERE p.user = :user");
        deletePosts.setParameter("user", user1);
        deletePosts.executeUpdate();

        deletePosts.setParameter("user", user2);
        deletePosts.executeUpdate();

        Query deleteUser = entityManager.createQuery("DELETE FROM User u WHERE u = :user");
        deleteUser.setParameter("user", user1);
        deleteUser.executeUpdate();
        deleteUser.setParameter("user", user2);
        deleteUser.executeUpdate();

        entityManager.flush();
    }
}