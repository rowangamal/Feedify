package com.example.backend.services;

import com.example.backend.dtos.ReportUserDTO;
import com.example.backend.entities.ReportUser;
import com.example.backend.exceptions.DuplicatedReportException;
import com.example.backend.exceptions.ReportNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.ReportUserRepository;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;


@Service
public class ReportUserService {
    @Autowired
    private ReportUserRepository reportUserRepository;
    @Autowired
    private UserRepository userRepository;

    public void reportUser(ReportUserDTO reportUserDTO){
        if(reportUserDTO.getReporterID() == reportUserDTO.getReportedID())
            throw new ReportNotFoundException("You can't report yourself");
        if(reportUserRepository.findReportUserByReporterIdAndReportedIdAndReason(reportUserDTO.getReporterID(),
                reportUserDTO.getReportedID(), reportUserDTO.getReason()) != null)
            throw new DuplicatedReportException("You have already reported this user");
        ReportUser reportUser = new ReportUser();
        reportUser.setReporter(userRepository.findUserById(reportUserDTO.getReporterID()).
                orElseThrow(() -> new UserNotFoundException("Reporter user not found")));
        reportUser.setReported(userRepository.findUserById(reportUserDTO.getReportedID()).
                orElseThrow(() -> new UserNotFoundException("Reported user not found")));
        reportUser.setReason(reportUserDTO.getReason());
        reportUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reportUserRepository.save(reportUser);
    }
}
