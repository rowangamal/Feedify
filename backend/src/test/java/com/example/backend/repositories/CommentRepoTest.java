package com.example.backend.repositories;

import com.example.backend.dtos.PostsResponseDTO;
import com.example.backend.dtos.PostsWrapperDTO;
import com.example.backend.entities.Comment;
import com.example.backend.entities.Post;
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

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class CommentRepoTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepo postRepo;

    @PersistenceContext
    private EntityManager entityManager;

    private User user;

    private Post post;

    private Comment comment1;

    private Comment comment2;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setEmail("email@example.com");
        user.setPassword("password123");
        entityManager.persist(user);

        post = new Post();
        post.setContent("Test post 1");
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setUser(user);
        entityManager.persist(post);

        comment1 = new Comment();
        comment1.setContent("Test comment 1");
        comment1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        comment1.setPost(post);
        comment1.setUser(user);
        entityManager.persist(comment1);

        comment2 = new Comment();
        comment2.setContent("Test comment 2");
        comment2.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        comment2.setPost(post);
        comment2.setUser(user);
        entityManager.persist(comment2);
    }

    @Test
    void addCommentSuccessfully() {
        List<Optional<Comment>> commentArr = commentRepository.getCommentsByPostId(post.getId());
        assertThat(commentArr.size()).isEqualTo(2);
        assertThat(commentArr.get(0).get().getContent()).isEqualTo("Test comment 1");

        List<PostsResponseDTO> posts = postRepo.getPostsOfUsers(List.of(user), 0, 10);
        PostsResponseDTO post = posts.get(0);
        assertThat(post.getCommentsCount()).isEqualTo(2);
    }

    @Test
    void deleteCommentSuccessfully() {
        commentRepository.removeComment(comment1.getId());
        List<Optional<Comment>> commentArr = commentRepository.getCommentsByPostId(post.getId());
        assertThat(commentArr.size()).isEqualTo(1);
        assertThat(commentArr.get(0).get().getContent()).isEqualTo("Test comment 2");

        List<PostsResponseDTO> posts = postRepo.getPostsOfUsers(List.of(user), 0, 10);
        PostsResponseDTO post = posts.get(0);
        assertThat(post.getCommentsCount()).isEqualTo(1);
    }

    @Test
    void addCommentWithEmptyContent() {
        Comment emptyComment = new Comment();
        emptyComment.setContent("");
        emptyComment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        emptyComment.setPost(post);
        emptyComment.setUser(user);
        entityManager.persist(emptyComment);

        List<Optional<Comment>> commentArr = commentRepository.getCommentsByPostId(post.getId());
        assertThat(commentArr.size()).isEqualTo(3);
        assertThat(commentArr.get(2).get().getContent()).isEqualTo("");

        List<PostsResponseDTO> posts = postRepo.getPostsOfUsers(List.of(user), 0, 10);
        PostsResponseDTO post = posts.get(0);
        assertThat(post.getCommentsCount()).isEqualTo(3);
    }

    @Test
    void deleteNonExistentComment() {
        int result = commentRepository.removeComment(999L);
        assertEquals(0, result);

        List<Optional<Comment>> commentArr = commentRepository.getCommentsByPostId(post.getId());
        assertThat(commentArr.size()).isEqualTo(2);

        List<PostsResponseDTO> posts = postRepo.getPostsOfUsers(List.of(user), 0, 10);
        PostsResponseDTO post = posts.get(0);
        assertThat(post.getCommentsCount()).isEqualTo(2);
    }

    @Test
    void addCommentWithFutureTimestamp() {
        Comment futureComment = new Comment();
        futureComment.setContent("Future comment");
        futureComment.setCreatedAt(new Timestamp(System.currentTimeMillis() + 1000000));
        futureComment.setPost(post);
        futureComment.setUser(user);
        entityManager.persist(futureComment);

        List<Optional<Comment>> commentArr = commentRepository.getCommentsByPostId(post.getId());
        assertThat(commentArr.size()).isEqualTo(3);
        assertThat(commentArr.get(2).get().getContent()).isEqualTo("Future comment");

        List<PostsResponseDTO> posts = postRepo.getPostsOfUsers(List.of(user), 0, 10);
        PostsResponseDTO post = posts.get(0);
        assertThat(post.getCommentsCount()).isEqualTo(3);
    }


    @AfterEach
    void tearDown() {
        Query deleteComments = entityManager.createQuery("DELETE FROM Comment c WHERE c.post = :post");
        deleteComments.setParameter("post", post);
        deleteComments.executeUpdate();

        Query deletePost = entityManager.createQuery("DELETE FROM Post p WHERE p = :post");
        deletePost.setParameter("post", post);
        deletePost.executeUpdate();

        Query deleteUser = entityManager.createQuery("DELETE FROM User u WHERE u = :user");
        deleteUser.setParameter("user", user);
        deleteUser.executeUpdate();

        entityManager.flush();
    }
}