package com.example.backend.services;

import com.example.backend.dtos.ReportPostDTO;
import com.example.backend.entities.Post;
import com.example.backend.entities.ReportPost;
import com.example.backend.exceptions.PostNoFoundException;
import com.example.backend.exceptions.ReportNotFoundException;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.ReportPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportPostService {
    @Autowired
    private ReportPostRepository reportPostRepository;
    @Autowired
    private PostRepository postRepository;


    public List<ReportPostDTO> getAllPostReports() {
        List<ReportPost> reportPosts = reportPostRepository.findByOrderByCreatedAtDesc();
        List<ReportPostDTO> reportPostDTOs = new ArrayList<>();
        if (reportPosts == null)
            throw new NullPointerException("Reports are null in database!");
        for (ReportPost reportPost : reportPosts) {
            reportPostDTOs.add(new ReportPostDTO(
                    reportPost.getId(),
                    reportPost.getPost().getId(),
                    reportPost.getUser().getId(),
                    reportPost.getUser().getEmail(),
                    reportPost.getReason(),
                    reportPost.getCreatedAt())
            );
        }
        return reportPostDTOs;
    }

//    @Transactional
    public void deletePost(long reportID){
        ReportPost reportPost = reportPostRepository.findReportPostById(reportID);
        if (reportPost == null)
            throw new ReportNotFoundException("Report not found");
        Post post = reportPost.getPost();
        if (post == null)
            throw new PostNoFoundException("Post not found");
        postRepository.delete(post);

    }

    public void denyReport(long reportID){
        ReportPost reportPost = reportPostRepository.findReportPostById(reportID);
        if (reportPost == null)
            throw new ReportNotFoundException("Report not found");
        reportPostRepository.delete(reportPost);
    }
}
