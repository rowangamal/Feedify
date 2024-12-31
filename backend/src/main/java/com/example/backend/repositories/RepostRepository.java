package com.example.backend.repositories;

import com.example.backend.entities.Repost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepostRepository extends JpaRepository<Repost, Long> {
    List<Repost> findByPostId(Long postId);
    boolean existsByPostIdAndUserId(Long postId, Long userId);
}