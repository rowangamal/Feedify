package com.example.backend.services;

import com.example.backend.dtos.InteractionsDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.Repost;
import com.example.backend.entities.User;
import com.example.backend.notifications.Notification;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.RepostRepository;
import com.example.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RepostServiceTest {

    @Mock
    private RepostRepository repostRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private Notification notification;


    @InjectMocks
    private RepostService repostService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRepostPost() {
        User user = new User();
        user.setId(1L);

        Post post = new Post();
        post.setId(1L);
        post.setRepostsCount(0);
        post.setUser(user);

        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(repostRepository.existsByPostIdAndUserId(1L, 1L)).thenReturn(false);
        doNothing().when(notification).sendNotificationRepost(anyString(), anyString(), anyLong());
        repostService.repostPost(1L);

        verify(repostRepository, times(1)).save(any(Repost.class));
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void testGetUsersWhoRepostedPost() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("amin@gmail.com");
        user1.setUsername("amin");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("destroyer_of_worlds@gmail.com");
        user2.setUsername("destroyer_of_worlds");

        Repost repost1 = new Repost();
        repost1.setUser(user1);

        Repost repost2 = new Repost();
        repost2.setUser(user2);

        when(repostRepository.findByPostId(1L)).thenReturn(Arrays.asList(repost1, repost2));
        List<InteractionsDTO> users = repostService.getUsersWhoRepostedPost(1L);

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("amin@gmail.com", users.get(0).getEmail());
        assertEquals("destroyer_of_worlds@gmail.com", users.get(1).getEmail());
    }

    @Test
    void testRepostAlreadyDone() {
        User user = new User();
        user.setId(1L);

        Post post = new Post();
        post.setId(1L);
        post.setRepostsCount(0);

        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(repostRepository.existsByPostIdAndUserId(1L, 1L)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> repostService.repostPost(1L));
        assertEquals("User has already reposted this post", exception.getMessage());
    }


    @Test
    void testRepostUserNotFound() {
        Post post = new Post();
        post.setId(1L);
        post.setRepostsCount(0);

        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> repostService.repostPost(1L));
        assertEquals("User not found with ID: 1", exception.getMessage());
    }

    @Test
    void testGetUsersWhoRepostedPostEmpty() {
        when(repostRepository.findByPostId(1L)).thenReturn(List.of());
        List<InteractionsDTO> users = repostService.getUsersWhoRepostedPost(1L);

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    void testRepostCountIncrement() {
        User user = new User();
        user.setId(1L);

        Post post = new Post();
        post.setId(1L);
        post.setRepostsCount(0);
        post.setUser(user);

        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(repostRepository.existsByPostIdAndUserId(1L, 1L)).thenReturn(false);
        doNothing().when(notification).sendNotificationRepost(anyString(), anyString(), anyLong());

        repostService.repostPost(1L);

        assertEquals(1, post.getRepostsCount());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void testRepostForAnotherUser() {
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        Post post = new Post();
        post.setId(1L);
        post.setRepostsCount(0);
        post.setUser(user1);

        when(userService.getUserId()).thenReturn(2L);
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(repostRepository.existsByPostIdAndUserId(1L, 2L)).thenReturn(false);
        doNothing().when(notification).sendNotificationRepost(anyString(), anyString(), anyLong());

        repostService.repostPost(1L);

        verify(repostRepository, times(1)).save(any(Repost.class));
        verify(postRepository, times(1)).save(post);
    }


    @Test
    void testRepostForInvalidUserId() {
        when(userService.getUserId()).thenReturn(-1L);  // Invalid user ID
        RuntimeException exception = assertThrows(RuntimeException.class, () -> repostService.repostPost(1L));
        assertEquals("User not found with ID: -1", exception.getMessage());
    }

    @Test
    void testRepostWithZeroReposts() {
        User user = new User();
        user.setId(1L);

        Post post = new Post();
        post.setId(1L);
        post.setRepostsCount(0);
        post.setUser(user);

        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(repostRepository.existsByPostIdAndUserId(1L, 1L)).thenReturn(false);
        doNothing().when(notification).sendNotificationRepost(anyString(), anyString(), anyLong());

        repostService.repostPost(1L);

        assertEquals(1, post.getRepostsCount());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void testRepostPostNotFound() {
        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> repostService.repostPost(1L));
        assertEquals("Post not found with ID: 1", exception.getMessage());
    }


    @Test
    void testRepostWithMultipleReposts() {
        User user = new User();
        user.setId(1L);

        Post post = new Post();
        post.setId(1L);
        post.setRepostsCount(3);
        post.setUser(user);

        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(repostRepository.existsByPostIdAndUserId(1L, 1L)).thenReturn(false);
        doNothing().when(notification).sendNotificationRepost(anyString(), anyString(), anyLong());

        repostService.repostPost(1L);
        assertEquals(4, post.getRepostsCount());
        verify(postRepository, times(1)).save(post);
    }

}
