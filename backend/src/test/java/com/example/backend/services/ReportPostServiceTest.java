package com.example.backend.services;

import com.example.backend.dtos.ReportPostDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.ReportPost;
import com.example.backend.entities.User;
import com.example.backend.exceptions.PostNoFoundException;
import com.example.backend.exceptions.ReportNotFoundException;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.ReportPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ReportPostServiceTest {
    @Mock
    private ReportPostRepository reportPostRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private ReportPostService reportPostService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    private User setUser(long id, String username, String password, String email){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }

    private Post setPost(long id, User user, String content){
        Post post = new Post();
        post.setId(id);
        post.setUser(user);
        post.setContent(content);
        return post;
    }

    private ReportPost setReportPost(long id, Post post, User user, String reason){
        ReportPost reportPost = new ReportPost();
        reportPost.setId(id);
        reportPost.setPost(post);
        reportPost.setUser(user);
        reportPost.setReason(reason);
        reportPost.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return reportPost;
    }

    private List<ReportPost> testData(){
        List<ReportPost> reportPosts = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = setUser(i%3 + 1, "user" + i, "password" + i, "email" + i);
            Post post = setPost(i, user, "content" + i);
            ReportPost reportPost = setReportPost(i, post, user, "reason" + i);
            reportPosts.add(reportPost);
        }
        return reportPosts;
    }

    @Test
    void getAllPostReports() {
        List<ReportPost> reportPosts = testData();
        when(reportPostRepository.findByOrderByCreatedAtDesc()).thenReturn(reportPosts);
        List<ReportPostDTO> reportPostDTOs = reportPostService.getAllPostReports();
        assertEquals(reportPosts.size(), reportPostDTOs.size());
        for (int i = 0; i < reportPosts.size(); i++) {
            assertEquals(reportPosts.get(i).getId(), reportPostDTOs.get(i).getReportID());
            assertEquals(reportPosts.get(i).getPost().getId(), reportPostDTOs.get(i).getPostID());
            assertEquals(reportPosts.get(i).getUser().getId(), reportPostDTOs.get(i).getUserID());
            assertEquals(reportPosts.get(i).getUser().getEmail(), reportPostDTOs.get(i).getEmail());
            assertEquals(reportPosts.get(i).getReason(), reportPostDTOs.get(i).getReason());
            System.out.println(reportPosts.get(i).getCreatedAt());
            System.out.println(reportPostDTOs.get(i).getReportTime());
            assertEquals(reportPosts.get(i).getCreatedAt(), reportPostDTOs.get(i).getReportTime());
        }
    }

    @Test
    void getAllPostReportsEmpty_ReturnsNonNull() {
        when(reportPostRepository.findByOrderByCreatedAtDesc()).thenReturn(new ArrayList<>());
        Executable executable = () -> reportPostService.getAllPostReports();
        assertNotNull(executable);
        assertDoesNotThrow(executable);
    }

    @Test
    void getAllPostReports_NullPointerException() {
        when(reportPostRepository.findByOrderByCreatedAtDesc()).thenReturn(null);
        Executable executable = () -> reportPostService.getAllPostReports();
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void deletePost_NoExceptionThrown(){
        ReportPost reportPost = testData().getFirst();
        when(reportPostRepository.findReportPostById(reportPost.getId())).thenReturn(reportPost);
        Executable executable = () -> reportPostService.deletePost(reportPost.getId());
        assertDoesNotThrow(executable);
    }

    @Test
    void deletePost_ReportNotFoundException(){
        ReportPost reportPost = testData().getFirst();
        when(reportPostRepository.findReportPostById(reportPost.getId())).thenReturn(null);
        Executable executable = () -> reportPostService.deletePost(reportPost.getId());
        assertThrows(ReportNotFoundException.class, executable);
    }

    @Test
    void deletePost_PostNoFoundException(){
        ReportPost reportPost = setReportPost(1, null,
                setUser(1, "reporter", "reporter", "reporter")
                , "reason");
        when(reportPostRepository.findReportPostById(reportPost.getId())).thenReturn(reportPost);
        Executable executable = () -> reportPostService.deletePost(reportPost.getId());
        assertThrows(PostNoFoundException.class, executable);
    }

    @Test
    void denyReport_NoExceptionThrown(){
        ReportPost reportPost = testData().getFirst();
        when(reportPostRepository.findReportPostById(reportPost.getId())).thenReturn(reportPost);
        Executable executable = () -> reportPostService.denyReport(reportPost.getId());
        assertDoesNotThrow(executable);
    }

    @Test
    void denyReport_ReportNotFoundException(){
        when(reportPostRepository.findReportPostById(1)).thenReturn(null);
        Executable executable = () -> reportPostService.denyReport(1);
        assertThrows(ReportNotFoundException.class, executable);
    }

}
