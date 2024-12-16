package com.example.backend.services;

import com.example.backend.dtos.ReportUserDTO;
import com.example.backend.entities.ReportUser;
import com.example.backend.entities.User;
import com.example.backend.exceptions.DuplicatedReportException;
import com.example.backend.exceptions.ReportNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.ReportUserRepository;
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

class ReportUserServiceTest {

    @Mock
    private ReportUserRepository reportUserRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReportUserService reportUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void reportUserSuccessfully() {
        ReportUserDTO reportUserDTO = new ReportUserDTO();
        reportUserDTO.setReporterID(1L);
        reportUserDTO.setReportedID(2L);
        reportUserDTO.setReason("Fake Identity");

        when(reportUserRepository.findReportUserByReporterIdAndReportedIdAndReason(anyLong(), anyLong(), anyString())).thenReturn(null);
        when(userRepository.findUserById(1L)).thenReturn(Optional.of(new User()));
        when(userRepository.findUserById(2L)).thenReturn(Optional.of(new User()));


        reportUserService.reportUser(reportUserDTO);

        verify(reportUserRepository, times(1)).save(any(ReportUser.class));
    }

    @Test
    void reportUserAlreadyReported() {
        ReportUserDTO reportUserDTO = new ReportUserDTO();
        reportUserDTO.setReporterID(1L);
        reportUserDTO.setReportedID(2L);
        reportUserDTO.setReason("Fake Identity");

        when(reportUserRepository.findReportUserByReporterIdAndReportedIdAndReason(anyLong(), anyLong(), anyString())).thenReturn(new ReportUser());

        assertThrows(DuplicatedReportException.class, () -> reportUserService.reportUser(reportUserDTO));
    }

    @Test
    void reportUserUserNotFound() {
        ReportUserDTO reportUserDTO = new ReportUserDTO();
        reportUserDTO.setReporterID(1L);
        reportUserDTO.setReportedID(2L);
        reportUserDTO.setReason("Fake Identity");

        when(reportUserRepository.findReportUserByReporterIdAndReportedIdAndReason(anyLong(), anyLong(), anyString())).thenReturn(null);
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> reportUserService.reportUser(reportUserDTO));
    }

    @Test
    void reportUserReportedUserNotFound() {
        ReportUserDTO reportUserDTO = new ReportUserDTO();
        reportUserDTO.setReporterID(1L);
        reportUserDTO.setReportedID(2L);
        reportUserDTO.setReason("Fake Identity");

        when(reportUserRepository.findReportUserByReporterIdAndReportedIdAndReason(anyLong(), anyLong(), anyString())).thenReturn(null);
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> reportUserService.reportUser(reportUserDTO));
    }

    @Test
    void reportUserReportYourself() {
        ReportUserDTO reportUserDTO = new ReportUserDTO();
        reportUserDTO.setReporterID(1L);
        reportUserDTO.setReportedID(1L);
        reportUserDTO.setReason("Fake Identity");

        assertThrows(ReportNotFoundException.class, () -> reportUserService.reportUser(reportUserDTO));
    }
}