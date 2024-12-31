package com.example.backend.services;

import com.example.backend.dtos.ReportUserDTO;
import com.example.backend.entities.ReportUser;
import com.example.backend.entities.User;
import com.example.backend.exceptions.DuplicatedReportException;

import com.example.backend.exceptions.ReportNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.notifications.Notification;
import com.example.backend.repositories.ReportUserRepository;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.sql.Timestamp;


@Service
public class ReportUserService {
    @Autowired
    private ReportUserRepository reportUserRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    @Autowired
    private Notification notification;

    public void reportUser(ReportUserDTO reportUserDTO){
        long reporterId = userService.getUserId();
        if(reportUserDTO.getReporterID() == reportUserDTO.getReportedID())
            throw new ReportNotFoundException("You can't report yourself");
        if(reportUserRepository.findReportUserByReporterIdAndReportedIdAndReason(reporterId,
                reportUserDTO.getReportedID(), reportUserDTO.getReason()) != null)
            throw new DuplicatedReportException("You have already reported this user");
        ReportUser reportUser = new ReportUser();
        reportUser.setReporter(userRepository.findUserById(reporterId).
                orElseThrow(() -> new UserNotFoundException("Reporter user not found")));
        reportUser.setReported(userRepository.findUserById(reportUserDTO.getReportedID()).
                orElseThrow(() -> new UserNotFoundException("Reported user not found")));
        reportUser.setReason(reportUserDTO.getReason());
        reportUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reportUserRepository.save(reportUser);

        String message = "%s reported %s".formatted(reportUser.getReporter().getUsername(), reportUser.getReported().getUsername());
        notification.sendNotificationReport(message, reportUser.getReporter().getPictureURL());
    }

    public List<ReportUserDTO> getAllUserReports() {
        List<ReportUser> reportUsers = reportUserRepository.findByOrderByCreatedAtDesc();
        List<ReportUserDTO> reportUserDTOs = new ArrayList<>();
        if (reportUsers == null)
            throw new NullPointerException("Reports are null in database!");
        for (ReportUser reportUser : reportUsers) {
            reportUserDTOs.add(new ReportUserDTO(
                    reportUser.getId(),
                    reportUser.getReporter().getId(),
                    reportUser.getReported().getId(),
                    reportUser.getReporter().getEmail(),
                    reportUser.getReported().getEmail(),
                    reportUser.getReason(),
                    reportUser.getCreatedAt())

            );
        }
        return reportUserDTOs;
    }

    public void deleteUser(long reportID){
        ReportUser reportUser = reportUserRepository.findReportUserById(reportID);
        if (reportUser == null)
            throw new ReportNotFoundException("Report not found");
        User user = reportUser.getReported();
        if (user == null)
            throw new UserNotFoundException("User not found");
        userRepository.delete(user);
    }

    public void denyReport(long reportID){
        ReportUser reportUser = reportUserRepository.findReportUserById(reportID);
        if (reportUser == null)
            throw new ReportNotFoundException("Report not found");
        reportUserRepository.delete(reportUser);
    }

}
