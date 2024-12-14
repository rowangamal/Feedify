package com.example.backend.repositories;

import com.example.backend.entities.ReportPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportPostRepository extends JpaRepository<ReportPost, Long> {
    List<ReportPost> findByOrderByCreatedAtDesc();
    ReportPost findReportPostById(long id);
}
