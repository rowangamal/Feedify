package com.example.backend.repositories;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class PostRepoTest {
    @InjectMocks
    PostRepo postRepo;

    @Mock
    EntityManager entityManager;

    @Test
    void getPostsOfUsers() {
    }

    @Test
    void getPostAndCreatorByTopics() {
    }

    @Test
    void getPostsByUser() {
    }
}