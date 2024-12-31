package com.example.backend.repositories;

import com.example.backend.entities.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<PostType, Long> {
    boolean existsByName(String name);

}
