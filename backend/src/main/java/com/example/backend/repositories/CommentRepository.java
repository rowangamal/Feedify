package com.example.backend.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO comment (content, created_at, post_id, user_id) VALUES (:content, :createdAt, :postId, :userId)", nativeQuery = true)
    void addComment(String content, long postId, long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comment WHERE id = :commentId", nativeQuery = true)
    int removeComment(long commentId);
}
