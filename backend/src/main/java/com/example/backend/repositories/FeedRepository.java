package com.example.backend.repositories;

import com.example.backend.postInteractions.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FeedRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
}
