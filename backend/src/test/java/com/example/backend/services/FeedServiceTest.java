package com.example.backend.services;

import com.example.backend.DTOs.FeedDTO;
import com.example.backend.Feed.FeedFactory;
import com.example.backend.Feed.IFeed;
import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedServiceTest {

    @InjectMocks
    private FeedService feedService;

    @Mock
    private EntityManager em;

    @Mock
    private FeedDTO feedDTO;

    @Mock
    private IFeed iFeed;

    @BeforeEach
    void setUp() {
        when(feedDTO.getTopics())
                .thenReturn(List.of("Sport", "Technology"));

        when(feedDTO.getUserId())
                .thenReturn(12);
    }

    @Test
    void testGetProfileFeedValidUser() {
        try (MockedStatic<FeedFactory> mockedFeedFactory = mockStatic(FeedFactory.class)) {
            mockedFeedFactory.when(() -> FeedFactory.getFeed("UserProfile")).thenReturn(iFeed);

            Post mockPost = createMockPost();
            List<Post> mockPosts = List.of(mockPost);
            when(iFeed.filter(eq(List.of("Sport", "Technology")), eq(12), eq(em)))
                    .thenReturn(mockPosts);

            List<Post> result = feedService.getProfileFeed(feedDTO);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("I love Dawy!", result.getFirst().getContent());

            verify(iFeed).filter(eq(List.of("Sport", "Technology")), eq(12), eq(em));
        }
    }

    @Test
    void testGetProfileFeedUnValidUser() {
        try (MockedStatic<FeedFactory> mockedFeedFactory = mockStatic(FeedFactory.class)) {
            mockedFeedFactory.when(() -> FeedFactory.getFeed("UserProfile")).thenReturn(iFeed);

            List<Post> result = feedService.getProfileFeed(feedDTO);

            assertNotNull(result);
            assertTrue(result.isEmpty(), "Expected an empty list when the user is not found");
        }
    }

    private Post createMockPost() {
        Post post = new Post();
        post.setContent("I love Dawy!");
        post.setLikesCount(10);
        post.setCommentsCount(5);
        post.setRepostsCount(2);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        User user = new User();
        user.setId(12);
        user.setUsername("testUser");
        user.setEmail("Dodo@example.com");
        user.setFName("Omar");
        user.setLName("Aldawy");
        user.setGender(true);
        post.setUser(user);

        return post;
    }
}