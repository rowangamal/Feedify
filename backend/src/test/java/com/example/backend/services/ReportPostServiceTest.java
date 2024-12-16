package com.example.backend.services;

import com.example.backend.dtos.ReportPostDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.ReportPost;
import com.example.backend.entities.User;
import com.example.backend.exceptions.PostNoFoundException;
import com.example.backend.exceptions.ReportNotFoundException;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.ReportPostRepository;
import com.example.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReportPostServiceTest {

    @Mock
    private ReportPostRepository reportPostRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReportPostService reportPostService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void reportPostSuccessfully() {
        ReportPostDTO reportPostDTO = new ReportPostDTO();
        reportPostDTO.setUserID(1L);
        reportPostDTO.setPostID(1L);
        reportPostDTO.setReason("Spam");

        when(reportPostRepository.findReportPostByUserIdAndPostIdAndReason(anyLong(), anyLong(), anyString())).thenReturn(null);
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(new Post()));
        when(userRepository.findUserById(anyLong())).thenReturn(Optional.of(new User()));

        reportPostService.reportPost(reportPostDTO);

        verify(reportPostRepository, times(1)).save(any(ReportPost.class));
    }

    @Test
    void reportPostAlreadyReported() {
        ReportPostDTO reportPostDTO = new ReportPostDTO();
        reportPostDTO.setUserID(1L);
        reportPostDTO.setPostID(1L);
        reportPostDTO.setReason("Spam");

        when(reportPostRepository.findReportPostByUserIdAndPostIdAndReason(anyLong(), anyLong(), anyString())).thenReturn(new ReportPost());

        assertThrows(ReportNotFoundException.class, () -> reportPostService.reportPost(reportPostDTO));
    }

    @Test
    void reportPostPostNotFound() {
        ReportPostDTO reportPostDTO = new ReportPostDTO();
        reportPostDTO.setUserID(1L);
        reportPostDTO.setPostID(1L);
        reportPostDTO.setReason("Spam");

        when(reportPostRepository.findReportPostByUserIdAndPostIdAndReason(anyLong(), anyLong(), anyString())).thenReturn(null);
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PostNoFoundException.class, () -> reportPostService.reportPost(reportPostDTO));
    }

    @Test
    void reportPostUserNotFound() {
        ReportPostDTO reportPostDTO = new ReportPostDTO();
        reportPostDTO.setUserID(1L);
        reportPostDTO.setPostID(1L);
        reportPostDTO.setReason("Spam");

        when(reportPostRepository.findReportPostByUserIdAndPostIdAndReason(anyLong(), anyLong(), anyString())).thenReturn(null);
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(new Post()));
        when(userRepository.findUserById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PostNoFoundException.class, () -> reportPostService.reportPost(reportPostDTO));
    }
}