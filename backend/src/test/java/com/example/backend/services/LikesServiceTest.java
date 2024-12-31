package com.example.backend.services;

import com.example.backend.entities.Like;
import com.example.backend.exceptions.LikeNotFoundException;
import com.example.backend.exceptions.MultipleLikeException;
import com.example.backend.exceptions.PostNoFoundException;
import com.example.backend.repositories.LikesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LikesServiceTest {

    @InjectMocks
    private LikesService likesService;

    @Mock
    private LikesRepository likesRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void likePostSuccessfully() {
        when(userService.getUserId()).thenReturn(1L);
        likesService.likePost(1L);
        verify(likesRepository).addLike(any(Timestamp.class), eq(1L), eq(1L));
    }

    @Test
    void likePostAlreadyLiked() {
        when(userService.getUserId()).thenReturn(1L);
        doThrow(new DataIntegrityViolationException("")).when(likesRepository).addLike(any(Timestamp.class), eq(1L), eq(1L));
        assertThrows(MultipleLikeException.class, () -> likesService.likePost(1L));
    }

    @Test
    void likePostNotFound() {
        when(userService.getUserId()).thenReturn(1L);
        doThrow(new NullPointerException("")).when(likesRepository).addLike(any(Timestamp.class), eq(1L), eq(1L));
        assertThrows(PostNoFoundException.class, () -> likesService.likePost(1L));
    }

    @Test
    void likePostInvalidData() {
        when(userService.getUserId()).thenReturn(1L);
        doThrow(new IllegalArgumentException("")).when(likesRepository).addLike(any(Timestamp.class), eq(1L), eq(1L));
        assertThrows(IllegalArgumentException.class, () -> likesService.likePost(1L));
    }

    @Test
    void likePostUnexpectedError() {
        when(userService.getUserId()).thenReturn(1L);
        doThrow(new RuntimeException("")).when(likesRepository).addLike(any(Timestamp.class), eq(1L), eq(1L));
        assertThrows(RuntimeException.class, () -> likesService.likePost(1L));
    }

    @Test
    void unlikePostSuccessfully() {
        when(userService.getUserId()).thenReturn(1L);
        when(likesRepository.removeLike(1L, 1L)).thenReturn(1);
        likesService.unlikePost(1L);
        verify(likesRepository).removeLike(1L, 1L);
    }

    @Test
    void unlikePostNotFound() {
        when(userService.getUserId()).thenReturn(1L);
        when(likesRepository.removeLike(1L, 1L)).thenReturn(0);
        assertThrows(LikeNotFoundException.class, () -> likesService.unlikePost(1L));
    }

    @Test
    void unlikePostInvalidData() {
        when(userService.getUserId()).thenReturn(1L);
        doThrow(new DataIntegrityViolationException("")).when(likesRepository).removeLike(1L, 1L);
        assertThrows(DataIntegrityViolationException.class, () -> likesService.unlikePost(1L));
    }

    @Test
    void unlikePostPostNotFound() {
        when(userService.getUserId()).thenReturn(1L);
        doThrow(new NullPointerException("")).when(likesRepository).removeLike(1L, 1L);
        assertThrows(NullPointerException.class, () -> likesService.unlikePost(1L));
    }

    @Test
    void unlikePostIllegalArguments() {
        when(userService.getUserId()).thenReturn(1L);
        doThrow(new IllegalArgumentException("")).when(likesRepository).removeLike(1L, 1L);
        assertThrows(IllegalArgumentException.class, () -> likesService.unlikePost(1L));
    }

    @Test
    void unlikePostUnexpectedError() {
        when(userService.getUserId()).thenReturn(1L);
        doThrow(new RuntimeException("")).when(likesRepository).removeLike(1L, 1L);
        assertThrows(RuntimeException.class, () -> likesService.unlikePost(1L));
    }

    @Test
    void checkIfPostIsLikedSuccessfully() {
        when(userService.getUserId()).thenReturn(1L);
        when(likesRepository.findLikeByPostIdAndUserId(1L, 1L)).thenReturn(Optional.of(new Like()));
        likesService.checkIfPostIsLiked(1L);
        verify(likesRepository).findLikeByPostIdAndUserId(1L, 1L);
    }

    @Test
    void checkIfPostIsLikedNotFound() {
        when(userService.getUserId()).thenReturn(1L);
        when(likesRepository.findLikeByPostIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
        assertThrows(LikeNotFoundException.class, () -> likesService.checkIfPostIsLiked(1L));
    }

    @Test
    void checkIfPostIsLikedInvalidData() {
        when(userService.getUserId()).thenReturn(1L);
        doThrow(new IllegalArgumentException("")).when(likesRepository).findLikeByPostIdAndUserId(1L, 1L);
        assertThrows(IllegalArgumentException.class, () -> likesService.checkIfPostIsLiked(1L));
    }

    @Test
    void checkIfPostIsLikedPostNotFound() {
        when(userService.getUserId()).thenReturn(1L);
        doThrow(new NullPointerException("")).when(likesRepository).findLikeByPostIdAndUserId(1L, 1L);
        assertThrows(PostNoFoundException.class, () -> likesService.checkIfPostIsLiked(1L));
    }

    @Test
    void checkIfPostIsLikedUnexpectedError() {
        when(userService.getUserId()).thenReturn(1L);
        doThrow(new RuntimeException("")).when(likesRepository).findLikeByPostIdAndUserId(1L, 1L);
        assertThrows(RuntimeException.class, () -> likesService.checkIfPostIsLiked(1L));
    }
}