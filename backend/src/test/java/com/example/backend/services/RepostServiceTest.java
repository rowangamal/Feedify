//package com.example.backend.services;
//
//import com.example.backend.entities.Post;
//import com.example.backend.entities.Repost;
//import com.example.backend.entities.User;
//import com.example.backend.repositories.PostRepository;
//import com.example.backend.repositories.RepostRepository;
//import com.example.backend.repositories.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import java.util.List;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//
//class RepostServiceTest {
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private RepostRepository repostRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PostRepository postRepository;
//
//    @InjectMocks
//    private RepostService repostService;
//
//    private User user;
//    private Post post;
//    private Repost repost;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        user = new User();
//        user.setId(1L);
//        user.setUsername("amin");
//
//        post = new Post();
//        post.setId(1L);
//        post.setContent("This is a test post");
//        post.setRepostsCount(1);
//
//        repost = new Repost();
//        repost.setId(1L);
//        repost.setUser(user);
//        repost.setPost(post);
//    }
//
//    @Test
//    void testRepostPost() {
//        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));
//        Mockito.when(userService.getUserId()).thenReturn(1L);
//
//        repostService.repostPost(1L);
//
//        Mockito.verify(repostRepository).save(Mockito.any(Repost.class));
//        Mockito.verify(postRepository).save(post);
//        assertEquals(2, post.getRepostsCount(), "Repost count should be incremented to 2");
//    }
//
//    @Test
//    void testRepostPost_PostNotFound() {
//        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.empty());
//
//        Mockito.when(userService.getUserId()).thenReturn(1L);
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            repostService.repostPost( 1L);
//        });
//        assertEquals("Post not found with ID: 1", exception.getMessage());
//    }
//
//    @Test
//    void testRepostPost_AlreadyReposted() {
//        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));
//        Mockito.when(repostRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(repost)); // Simulate repost already exists
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            repostService.repostPost( 1L);
//        });
//        assertEquals("User has already reposted this post", exception.getMessage());
//    }
//
//    @Test
//    void testGetAllRepostsByUser() {
//        Mockito.when(repostRepository.findByUserId(1L)).thenReturn(List.of(repost));
//        Mockito.when(userService.getUserId()).thenReturn(1L);
//
//        var reposts = repostService.getAllRepostsByUser(1L);
//
//        assertNotNull(reposts);
//        assertEquals(1, reposts.size(), "Should return 1 repost");
//    }
//
//    @Test
//    void testDeleteRepost() {
//        Mockito.when(repostRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(repost));
//        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));
//        Mockito.when(userService.getUserId()).thenReturn(1L);
//
//        repostService.deleteRepost(1L, 1L);
//
//        Mockito.verify(repostRepository).delete(repost);
//        Mockito.verify(postRepository).save(post);
//        assertEquals(0, post.getRepostsCount(), "Repost count should be decremented to 0");
//    }
//
//
//    @Test
//    void testDeleteRepost_NotFound() {
//        Mockito.when(repostRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
//        Mockito.when(userService.getUserId()).thenReturn(1L);
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            repostService.deleteRepost(1L, 1L);
//        });
//        assertEquals("Repost not found or does not belong to the user", exception.getMessage());
//    }
//
//}
