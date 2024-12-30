package com.example.backend.repositories;

import com.example.backend.entities.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO comment (content, created_at, post_id, user_id) VALUES (:content, :createdAt, :postId, :userId)", nativeQuery = true)
    void addComment(String content, Timestamp createdAt, long postId, long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comment WHERE id = :commentId", nativeQuery = true)
    int removeComment(long commentId);

    @Query(value = "SELECT * FROM comment WHERE post_id = :postId", nativeQuery = true)
    List<Optional<Comment>> getCommentsByPostId(long postId);
}
