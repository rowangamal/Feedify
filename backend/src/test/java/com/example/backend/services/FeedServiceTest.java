package com.example.backend.services;

import com.example.backend.dtos.FeedDTO;
import com.example.backend.dtos.PostDTO;
import com.example.backend.dtos.PostsResponseDTO;
import com.example.backend.entities.Post;
import com.example.backend.repositories.PostRepo;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.UserRepo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedServiceTest {
    @InjectMocks
    FeedService feedService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PostRepo postRepo;

    @Mock
    private FeedDTO feedDTO;

    @Test
    void getProfileFeedContainingPosts() {
        Post mockPost = mock(Post.class);
        when(mockPost.getContent()).thenReturn("Mocked content");
        when(userService.getUserId()).thenReturn(1L);
        when(postRepo.getPostsByUser(1L)).thenReturn(List.of(mockPost));
        when(feedDTO.getUserId()).thenReturn(1L);
        List<Post> result = feedService.getProfileFeed(feedDTO);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Mocked content", result.getFirst().getContent());
    }

    @Test
    void getProfileFeedNotContainingPosts() {
        when(userService.getUserId()).thenReturn(1L);
        when(postRepo.getPostsByUser(1L)).thenReturn(List.of());
        when(feedDTO.getUserId()).thenReturn(1L);
        List<Post> result = feedService.getProfileFeed(feedDTO);

        assertNotNull(result);
        assertEquals(0, result.size());
        assertThrowsExactly(NoSuchElementException.class, result::getFirst);
    }

    @Test
    void getFollowingFeedContainingPosts() {
        PostsResponseDTO mockPostsResponseDTO = mock(PostsResponseDTO.class);
        when(mockPostsResponseDTO.getContent()).thenReturn("Mocked content");
        when(userService.getUserId()).thenReturn(1L);
        when(userRepo.getFollowedUsersOfUser(1L)).thenReturn(List.of());
        when(postRepo.getPostsOfUsers(List.of())).thenReturn(List.of(mockPostsResponseDTO));
        when(feedDTO.getUserId()).thenReturn(1L);
        List<PostsResponseDTO> result = feedService.getFollowingFeed(feedDTO);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Mocked content", result.getFirst().getContent());
    }

    @Test
    void getFollowingFeedNotContainingPosts() {
        when(userService.getUserId()).thenReturn(1L);
        when(userRepo.getFollowedUsersOfUser(1L)).thenReturn(List.of());
        when(postRepo.getPostsOfUsers(List.of())).thenReturn(List.of());
        when(feedDTO.getUserId()).thenReturn(1L);
        List<PostsResponseDTO> result = feedService.getFollowingFeed(feedDTO);

        assertNotNull(result);
        assertEquals(0, result.size());
        assertThrowsExactly(NoSuchElementException.class, result::getFirst);
    }

    @Test
    void getTopicsFeedContainingPost() {
        PostsResponseDTO mockPostsResponseDTO = mock(PostsResponseDTO.class);
        when(mockPostsResponseDTO.getContent()).thenReturn("Mocked content");
        when(userService.getUserId()).thenReturn(1L);
        when(userRepo.getUserInterests(1L)).thenReturn(List.of());
        when(postRepo.getPostAndCreatorByTopics(List.of())).thenReturn(List.of(mockPostsResponseDTO));
        when(feedDTO.getUserId()).thenReturn(1L);
        List<PostsResponseDTO> result = feedService.getTopicsFeed(feedDTO);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Mocked content", result.getFirst().getContent());
    }

    @Test
    void getTopicsFeedNotContainingPost() {
        when(userService.getUserId()).thenReturn(1L);
        when(userRepo.getUserInterests(1L)).thenReturn(List.of());
        when(postRepo.getPostAndCreatorByTopics(List.of())).thenReturn(List.of());
        when(feedDTO.getUserId()).thenReturn(1L);
        List<PostsResponseDTO> result = feedService.getTopicsFeed(feedDTO);

        assertNotNull(result);
        assertEquals(0, result.size());
        assertThrowsExactly(NoSuchElementException.class, result::getFirst);
    }
}