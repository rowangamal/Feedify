package com.example.backend.services;

import com.example.backend.dtos.ReportUserDTO;
import com.example.backend.entities.ReportUser;
import com.example.backend.entities.User;
import com.example.backend.exceptions.ReportNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.ReportUserRepository;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportUserService {
    @Autowired
    private ReportUserRepository reportUserRepository;
    @Autowired
    private UserRepository userRepository;

    public List<ReportUserDTO> getAllUserReports() {
        List<ReportUser> reportUsers = reportUserRepository.findByOrderByCreatedAtDesc();
        List<ReportUserDTO> reportUserDTOs = new ArrayList<>();
        for (ReportUser reportUser : reportUsers) {
            reportUserDTOs.add(new ReportUserDTO(reportUser.getId(),
                    reportUser.getReporter().getId(),
                    reportUser.getReported().getId(),
                    reportUser.getReporter().getUsername(),
                    reportUser.getReported().getUsername(),
                    reportUser.getReason())
            );
        }
        return reportUserDTOs;
    }

//    @Transactional
    public void deleteUser(long reportID){
        ReportUser reportUser = reportUserRepository.findReportUserById(reportID);
        if (reportUser == null)
            throw new ReportNotFoundException("Report not found");
        User user = reportUser.getReported();
        if (user == null)
            throw new UserNotFoundException("User not found");
//        if(reportUser.getReporter().getId() == reportUser.getReported().getId())
//            throw new ReportNotFound("You can't report yourself");
        userRepository.delete(user);
    }

    public void denyReport(long reportID){
        ReportUser reportUser = reportUserRepository.findReportUserById(reportID);
        if (reportUser == null)
            throw new ReportNotFoundException("Report not found");
        reportUserRepository.delete(reportUser);
    }
}
