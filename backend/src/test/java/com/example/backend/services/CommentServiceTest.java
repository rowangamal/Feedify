package com.example.backend.services;

import com.example.backend.dtos.CommentDTO;
import com.example.backend.dtos.CommentsDTO;
import com.example.backend.entities.Comment;
import com.example.backend.entities.User;
import com.example.backend.exceptions.CommentNotFoundException;
import com.example.backend.exceptions.PostNoFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.notifications.Notification;
import com.example.backend.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @Mock
    private Notification notification;

    @Mock
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCommentSuccessfully() {
        CommentDTO commentDTO = new CommentDTO(1L, "content", 1L, new Timestamp(System.currentTimeMillis()));
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPictureURL("pic_url");

        when(userService.getCurrentUser()).thenReturn(Optional.of(user));
        when(postService.getPostAuthorId(1L)).thenReturn(2L);
        doNothing().when(notification).sendNotificationRepost(anyString(), anyString(), anyLong());
        commentService.addComment(commentDTO);

        verify(commentRepository).addComment(eq("content"), any(Timestamp.class), eq(1L), eq(1L));
        verify(notification).sendNotificationComment(anyString(), eq("pic_url"), eq(2L));
    }

    @Test
    void addCommentUserNotFound() {
        CommentDTO commentDTO = new CommentDTO(1L, "content", 1L, new Timestamp(System.currentTimeMillis()));

        when(userService.getCurrentUser()).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> commentService.addComment(commentDTO));
    }

    @Test
    void addCommentDataIntegrityViolation() {
        CommentDTO commentDTO = new CommentDTO(1L, "content", 1L, new Timestamp(System.currentTimeMillis()));
        User user = new User();
        user.setId(1L);

        when(userService.getCurrentUser()).thenReturn(Optional.of(user));
        doThrow(new DataIntegrityViolationException("")).when(commentRepository).addComment(anyString(), any(Timestamp.class), anyLong(), anyLong());

        assertThrows(DataIntegrityViolationException.class, () -> commentService.addComment(commentDTO));
    }

    @Test
    void addCommentNullPointerException() {
        CommentDTO commentDTO = new CommentDTO(1L, "content", 1L, new Timestamp(System.currentTimeMillis()));
        User user = new User();
        user.setId(1L);

        when(userService.getCurrentUser()).thenReturn(Optional.of(user));
        doThrow(new NullPointerException()).when(commentRepository).addComment(anyString(), any(Timestamp.class), anyLong(), anyLong());

        assertThrows(NullPointerException.class, () -> commentService.addComment(commentDTO));
    }

    @Test
    void addCommentRuntimeException() {
        CommentDTO commentDTO = new CommentDTO(1L, "content", 1L, new Timestamp(System.currentTimeMillis()));
        User user = new User();
        user.setId(1L);

        when(userService.getCurrentUser()).thenReturn(Optional.of(user));
        doThrow(new RuntimeException()).when(commentRepository).addComment(anyString(), any(Timestamp.class), anyLong(), anyLong());

        assertThrows(RuntimeException.class, () -> commentService.addComment(commentDTO));
    }

    @Test
    void deleteCommentSuccessfully() {
        when(commentRepository.removeComment(1L)).thenReturn(1);

        commentService.deleteComment(1L);

        verify(commentRepository).removeComment(1L);
    }

    @Test
    void deleteCommentNotFound() {
        when(commentRepository.removeComment(1L)).thenReturn(0);

        assertThrows(CommentNotFoundException.class, () -> commentService.deleteComment(1L));
    }

    @Test
    void deleteCommentDataIntegrityViolation() {
        when(commentRepository.removeComment(1L)).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(DataIntegrityViolationException.class, () -> commentService.deleteComment(1L));
    }

    @Test
    void deleteCommentRuntimeException() {
        when(commentRepository.removeComment(1L)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> commentService.deleteComment(1L));
    }

    @Test
    void getCommentsByPostIdSuccessfully() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("content");
        User user = new User();
        user.setUsername("username");
        user.setPictureURL("pic_url");
        comment.setUser(user);
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        when(commentRepository.getCommentsByPostId(1L)).thenReturn(List.of(Optional.of(comment)));

        CommentsDTO[] comments = commentService.getCommentsByPostId(1L);

        assertEquals(1, comments.length);
        assertEquals("content", comments[0].getContent());
        assertEquals("username", comments[0].getUsername());
        assertEquals("pic_url", comments[0].getPictureURL());
    }

    @Test
    void getCommentsByPostIdPostNotFound() {
        when(commentRepository.getCommentsByPostId(1L)).thenThrow(new PostNoFoundException("Post not found"));

        assertThrows(PostNoFoundException.class, () -> commentService.getCommentsByPostId(1L));
    }
}