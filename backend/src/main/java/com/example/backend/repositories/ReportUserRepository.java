package com.example.backend.repositories;

import com.example.backend.dtos.ReportUserDTO;
import com.example.backend.entities.ReportUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportUserRepository extends JpaRepository<ReportUser, Long> {
    List<ReportUser> findByOrderByCreatedAtDesc();
    ReportUser findReportUserById(long id);
    ReportUser findReportUserByReporterIdAndReportedIdAndReason(long reporterId, long reportedId, String reason);
}
