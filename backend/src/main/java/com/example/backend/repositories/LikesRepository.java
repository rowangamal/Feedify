package com.example.backend.repositories;

import com.example.backend.entities.Like;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface LikesRepository extends JpaRepository<Like, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO likes (created_at, post_id, user_id) VALUES (:createdAt, :postId, :userId)", nativeQuery = true)
    void addLike(@Param("createdAt") Timestamp createdAt, @Param("postId") long postId, @Param("userId") long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM likes WHERE post_id = :postId AND user_id = :userId", nativeQuery = true)
    void removeLike(@Param("postId") long postId, @Param("userId") long userId);
}

