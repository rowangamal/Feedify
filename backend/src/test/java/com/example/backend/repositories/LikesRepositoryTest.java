package com.example.backend.repositories;

import com.example.backend.dtos.PostsResponseDTO;
import com.example.backend.entities.Like;
import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class LikesRepositoryTest {
    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private PostRepo postRepo;

    @PersistenceContext
    private EntityManager entityManager;

    private User user1;

    private User user2;

    private Post post;

    private Like like1;

    private Like like2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setUsername("testuser1");
        user1.setEmail("email1@example.com");
        user1.setPassword("password123");
        entityManager.persist(user1);

        user2 = new User();
        user2.setUsername("testuser2");
        user2.setEmail("emai2l@example.com");
        user2.setPassword("password123");
        entityManager.persist(user2);

        post = new Post();
        post.setContent("Test post 1");
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setUser(user1);
        entityManager.persist(post);

        like1 = new Like();
        like1.setPost(post);
        like1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        like1.setUser(user1);
        entityManager.persist(like1);

        like2 = new Like();
        like2.setPost(post);
        like2.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        like2.setUser(user2);
        entityManager.persist(like2);
    }

    @Test
    void addLike() {
        Optional<Like> like1 = likesRepository.findLikeByPostIdAndUserId(post.getId(),user1.getId());
        Optional<Like> like2 = likesRepository.findLikeByPostIdAndUserId(post.getId(),user2.getId());

        assertThat(like1).isNotNull();
        assertThat(like2).isNotNull();

        List<PostsResponseDTO> posts = postRepo.getPostsOfUsers(List.of(user1), 0, 10);
        PostsResponseDTO post = posts.get(0);
        assertThat(post.getLikesCount()).isEqualTo(2);
    }

    @Test
    void removeLike() {
        likesRepository.removeLike(post.getId(), user1.getId());

        Optional<Like> like1 = likesRepository.findLikeByPostIdAndUserId(post.getId(),user1.getId());
        Optional<Like> like2 = likesRepository.findLikeByPostIdAndUserId(post.getId(),user2.getId());

        assertThat(like1).isEmpty();
        assertThat(like2).isNotNull();

        List<PostsResponseDTO> posts = postRepo.getPostsOfUsers(List.of(user1), 0, 10);
        PostsResponseDTO post = posts.get(0);
        assertThat(post.getLikesCount()).isEqualTo(1);
    }


    @AfterEach
    void tearDown() {
        Query deleteLikes = entityManager.createQuery("DELETE FROM Like l WHERE l.post = :post");
        deleteLikes.setParameter("post", post);
        deleteLikes.executeUpdate();

        Query deletePost = entityManager.createQuery("DELETE FROM Post p WHERE p = :post");
        deletePost.setParameter("post", post);
        deletePost.executeUpdate();

        Query deleteUser = entityManager.createQuery("DELETE FROM User u WHERE u = :user");
        deleteUser.setParameter("user", user1);
        deleteUser.executeUpdate();

        deleteUser.setParameter("user", user2);
        deleteUser.executeUpdate();

        entityManager.flush();
    }
}