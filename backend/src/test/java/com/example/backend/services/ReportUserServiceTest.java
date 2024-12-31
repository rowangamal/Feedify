package com.example.backend.services;

import com.example.backend.dtos.ReportUserDTO;
import com.example.backend.entities.ReportUser;
import com.example.backend.entities.User;

import com.example.backend.exceptions.ReportNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.notifications.Notification;
import com.example.backend.repositories.ReportPostRepository;
import com.example.backend.exceptions.DuplicatedReportException;

import com.example.backend.repositories.ReportUserRepository;
import com.example.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReportUserServiceTest {

    @Mock
    private ReportUserRepository reportUserRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private Notification notification;

    @InjectMocks
    private ReportUserService reportUserService;

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

    private ReportUser setReportUser(long id, User reporter, User reported, String reason){
        ReportUser reportUser = new ReportUser();
        reportUser.setId(id);
        reportUser.setReporter(reporter);
        reportUser.setReported(reported);
        reportUser.setReason(reason);
        reportUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return reportUser;
    }

    private List<ReportUser> testData(){
        List<ReportUser> reportUsers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            reportUsers.add(setReportUser(i,
                    setUser(i%3 + 1, "reporter" + i, "reporter" + i, "reporter" + i),
                    setUser(i, "reported" + i, "reported" + i, "reported" + i),
                    "REASON" + i));
        }
        return reportUsers;
    }

    @Test
    void getAllUserReports() {
        List<ReportUser> reportUsers = testData();
        when(reportUserRepository.findByOrderByCreatedAtDesc()).thenReturn(testData());
        List<ReportUserDTO> reportUserDTOs = reportUserService.getAllUserReports();
        assertEquals(reportUsers.size(), reportUserDTOs.size());
        for (int i = 0; i < reportUsers.size(); i++) {
            assertEquals(reportUsers.get(i).getId(), reportUserDTOs.get(i).getReportID());
            assertEquals(reportUsers.get(i).getReporter().getId(), reportUserDTOs.get(i).getReporterID());
            assertEquals(reportUsers.get(i).getReported().getId(), reportUserDTOs.get(i).getReportedID());
            assertEquals(reportUsers.get(i).getReporter().getEmail(), reportUserDTOs.get(i).getEmailReporter());
            assertEquals(reportUsers.get(i).getReported().getEmail(), reportUserDTOs.get(i).getEmailReported());
            assertEquals(reportUsers.get(i).getReason(), reportUserDTOs.get(i).getReason());
            System.out.println(reportUsers.get(i).getCreatedAt());
            System.out.println(reportUserDTOs.get(i).getReportTime());
//            assertEquals(reportUsers.get(i).getCreatedAt(), reportUserDTOs.get(i).getReportTime());
        }
    }


    @Test
    void getAllUserReportsEmpty_ReturnsNonNull() {
        when(reportUserRepository.findByOrderByCreatedAtDesc()).thenReturn(new ArrayList<>());
        Executable executable = () -> reportUserService.getAllUserReports();
        assertDoesNotThrow(executable);
    }

    @Test
    void getAllUsersReports_NullPointerException() {
        when(reportUserRepository.findByOrderByCreatedAtDesc()).thenReturn(null);
        Executable executable = () -> reportUserService.getAllUserReports();
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void deleteUser_NoExceptionThrown() {
        ReportUser reportUser = testData().getFirst();
        when(reportUserRepository.findReportUserById(1)).thenReturn(reportUser);
        Executable executable = () -> reportUserService.deleteUser(1);
        assertDoesNotThrow(executable);
    }

    @Test
    void deleteUser_ReportNotFoundException() {
        when(reportUserRepository.findReportUserById(1)).thenReturn(null);
        Executable executable = () -> reportUserService.deleteUser(1);
        assertThrows(ReportNotFoundException.class, executable);
    }

//    doesn't make sense bec of cascade option ensures that any reports would be deleted if user is deleted
//    but for the sake of ensuring our server never crashes
    @Test
    void deleteUser_UserNotFoundException() {
        ReportUser reportUser = setReportUser(1,
                setUser(1, "reporter", "reporter", "reporter"),
                null,
                "Violence");
        when(reportUserRepository.findReportUserById(1)).thenReturn(reportUser);
        Executable executable = () -> reportUserService.deleteUser(1);
        assertThrows(UserNotFoundException.class, executable);
    }

    @Test
    void denyReport_NoExceptionThrown() {
        ReportUser reportUser = testData().getFirst();
        when(reportUserRepository.findReportUserById(1)).thenReturn(reportUser);
        Executable executable = () -> reportUserService.denyReport(1);
        assertDoesNotThrow(executable);
    }

    @Test
    void denyReport_ReportNotFoundException() {
        when(reportUserRepository.findReportUserById(1)).thenReturn(null);
        Executable executable = () -> reportUserService.denyReport(1);
        assertThrows(ReportNotFoundException.class, executable);
    }

    @Test
    void reportUserSuccessfully() {
        ReportUserDTO reportUserDTO = new ReportUserDTO();
        reportUserDTO.setReporterID(1L);
        reportUserDTO.setReportedID(2L);
        reportUserDTO.setReason("Fake Identity");

        when(reportUserRepository.findReportUserByReporterIdAndReportedIdAndReason(
                anyLong(), anyLong(), anyString())).thenReturn(null);
        when(userRepository.findUserById(1L)).thenReturn(Optional.of(new User()));
        when(userRepository.findUserById(2L)).thenReturn(Optional.of(new User()));
        when(userService.getUserId()).thenReturn(1L);
        doNothing().when(notification).sendNotificationRepost(anyString(), anyString(), anyLong());

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
        when(userService.getUserId()).thenReturn(1L);

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
        when(userService.getUserId()).thenReturn(1L);

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
        when(userService.getUserId()).thenReturn(1L);

        assertThrows(UserNotFoundException.class, () -> reportUserService.reportUser(reportUserDTO));
    }

    @Test
    void reportUserReportYourself() {
        ReportUserDTO reportUserDTO = new ReportUserDTO();
        reportUserDTO.setReporterID(1L);
        reportUserDTO.setReportedID(1L);
        reportUserDTO.setReason("Fake Identity");
        when(userService.getUserId()).thenReturn(1L);

        assertThrows(ReportNotFoundException.class, () -> reportUserService.reportUser(reportUserDTO));
    }

 
}