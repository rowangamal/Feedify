package com.example.backend.services;

import com.example.backend.dtos.FeedDTO;
import com.example.backend.dtos.PostsResponseDTO;
import com.example.backend.dtos.PostsWrapperDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import com.example.backend.repositories.PostRepo;
import com.example.backend.repositories.UserRepo;
import com.example.backend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Mock
    private UserRepository userRepository;

    @Test
    void getPersonalProfileFeedContainingPosts() throws Exception {
        Post mockPost = mock(Post.class);
        when(mockPost.getContent()).thenReturn("Mocked content");
        User mockUser = mock(User.class);
        when(mockUser.getId()).thenReturn(1L);
        when(userRepository.findUsersByUsername("username")).thenReturn(Optional.of(mockUser));
        when(userService.getUserId()).thenReturn(1L);
        when(postRepo.getPostsByUser(1L, 0, 10)).thenReturn(List.of(mockPost));
        when(feedDTO.getUsername()).thenReturn("username");
        when(feedDTO.getPageSize()).thenReturn(10);
        when(postRepo.getPostsCountByUser(1L)).thenReturn(1);
        PostsWrapperDTO result = feedService.getPersonalProfileFeed(feedDTO);

        assertNotNull(result);
        assertEquals(1, result.getPosts().size());
        assertEquals("Mocked content", result.getPosts().getFirst().getContent());
    }

    @Test
    void getPersonalProfileFeedNotContainingPosts() throws Exception {
        User mockUser = mock(User.class);
        when(mockUser.getId()).thenReturn(1L);
        when(userRepository.findUsersByUsername("username")).thenReturn(Optional.of(mockUser));
        when(userService.getUserId()).thenReturn(1L);
        when(postRepo.getPostsByUser(1L, 0, 10)).thenReturn(List.of());
        when(feedDTO.getUsername()).thenReturn("username");
        when(feedDTO.getPageSize()).thenReturn(10);
        PostsWrapperDTO result = feedService.getPersonalProfileFeed(feedDTO);

        assertNotNull(result);
        assertEquals(0, result.getPosts().size());
        assertThrowsExactly(NoSuchElementException.class, result.getPosts()::getFirst);
    }

    @Test
    void getFollowingFeedContainingPosts() throws Exception {
        PostsResponseDTO mockPostsResponseDTO = mock(PostsResponseDTO.class);
        when(mockPostsResponseDTO.getContent()).thenReturn("Mocked content");
        when(userService.getUserId()).thenReturn(1L);
        when(userRepo.getFollowedUsersOfUser(1L)).thenReturn(List.of());
        when(postRepo.getPostsOfUsers(List.of(), 0, 10)).thenReturn(List.of(mockPostsResponseDTO));
        when(feedDTO.getUserId()).thenReturn(1L);
        when(feedDTO.getPageSize()).thenReturn(10);
        PostsWrapperDTO result = feedService.getFollowingFeed(feedDTO);

        assertNotNull(result);
        assertEquals(1, result.getPostResponses().size());
        assertEquals("Mocked content", result.getPostResponses().getFirst().getContent());
    }

    @Test
    void getFollowingFeedNotContainingPosts() throws Exception {
        when(userService.getUserId()).thenReturn(1L);
        when(userRepo.getFollowedUsersOfUser(1L)).thenReturn(List.of());
        when(postRepo.getPostsOfUsers(List.of(), 0, 10)).thenReturn(List.of());
        when(feedDTO.getUserId()).thenReturn(1L);
        when(feedDTO.getPageSize()).thenReturn(10);
        PostsWrapperDTO result = feedService.getFollowingFeed(feedDTO);

        assertNotNull(result);
        assertEquals(0, result.getPostResponses().size());
        assertThrowsExactly(NoSuchElementException.class, result.getPostResponses()::getFirst);
    }

    @Test
    void getTopicsFeedContainingPost() throws Exception {
        PostsResponseDTO mockPostsResponseDTO = mock(PostsResponseDTO.class);
        when(mockPostsResponseDTO.getContent()).thenReturn("Mocked content");
        when(userService.getUserId()).thenReturn(1L);
        when(userRepo.getUserInterests(1L)).thenReturn(List.of());
        when(postRepo.getPostAndCreatorByTopics(List.of(), 0, 10)).thenReturn(List.of(mockPostsResponseDTO));
        when(feedDTO.getUserId()).thenReturn(1L);
        when(feedDTO.getPageSize()).thenReturn(10);
        PostsWrapperDTO result = feedService.getTopicsFeed(feedDTO);

        assertNotNull(result);
        assertEquals(1, result.getPostResponses().size());
        assertEquals("Mocked content", result.getPostResponses().getFirst().getContent());
    }

    @Test
    void getTopicsFeedNotContainingPost() throws Exception {
        when(userService.getUserId()).thenReturn(1L);
        when(userRepo.getUserInterests(1L)).thenReturn(List.of());
        when(postRepo.getPostAndCreatorByTopics(List.of(), 0, 10)).thenReturn(List.of());
        when(feedDTO.getUserId()).thenReturn(1L);
        when(feedDTO.getPageSize()).thenReturn(10);
        PostsWrapperDTO result = feedService.getTopicsFeed(feedDTO);

        assertNotNull(result);
        assertEquals(0, result.getPostResponses().size());
        assertThrowsExactly(NoSuchElementException.class, result.getPostResponses()::getFirst);
    }
}