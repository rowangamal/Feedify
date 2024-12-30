package com.example.backend.repositories;

import com.example.backend.entities.Repost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepostRepository extends JpaRepository<Repost, Long> {
    List<Repost> findByUserId(Long userId);
    Optional<Repost> findByIdAndUserId(Long repostId, Long userId);
}
