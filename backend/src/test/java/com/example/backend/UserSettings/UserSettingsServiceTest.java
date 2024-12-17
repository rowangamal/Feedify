package com.example.backend.UserSettings;

import com.example.backend.dtos.ChangePasswordDTO;
import com.example.backend.dtos.UserInfoDTO;
import com.example.backend.entities.User;
import com.example.backend.exceptions.InvalidCredentialsException;
import com.example.backend.fileHandling.HandleListJson;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.UserService;
import com.example.backend.services.UserSettingsInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UserSettingsServiceTest {
    @InjectMocks
    private UserSettingsInfo userSettingsService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HandleListJson handleListJson;

    private AutoCloseable mocks;
    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
    }

    @Test
    void getUserSettingsTest() {
        //given
        User user = new User();
        user.setUsername("username");
        user.setPictureURL("pictureURL");
        UserInfoDTO userInfoDTO = new UserInfoDTO("username", "pictureURL");

        //when
        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserInfoDTO userInfoDTOTest = userSettingsService.getUserSettings();


        //then
        assertEquals(userInfoDTO.profilePic, userInfoDTOTest.profilePic);
        assertEquals(userInfoDTO.username, userInfoDTOTest.username);
    }

    @Test
    void getInterestsTest() {
        //given
        User user = new User();
        List<Long> interests = List.of(1L, 2L, 3L);
        //when
        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.getAllInterests(1L)).thenReturn(interests);
        List<Long> interestTest = userSettingsService.getInterests();
        //then
        assertEquals(interestTest, interests);
    }
    @Test
    void changeUsernameTest() {
        //given
        User user = new User();
        user.setUsername("username");
        //when
        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        String username = "newUsername";
        String usernameTest = userSettingsService.changeUsername(username);
        //then
        assertEquals(username, usernameTest);
    }

    @Test
    void changePasswordWithCorrectOldTest() {
        //given
        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode("oldPassword"));

        //when
        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.newPassword = "newPassword";
        changePasswordDTO.oldPassword = "oldPassword";
        String passwordTest = userSettingsService.changePassword(changePasswordDTO);
        //then
        assertEquals("Password changed successfully", passwordTest);
    }
    @Test
    void changePasswordWithInCorrectOldTest() {
        //given
        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode("InoldPassword"));

        //when
        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.newPassword = "newPassword";
        changePasswordDTO.oldPassword = "oldPassword";

        //then
        assertThrows(InvalidCredentialsException.class , () -> userSettingsService.changePassword(changePasswordDTO));
    }

    @Test
    void changeProfilePicTest() {
        //given
        User user = new User();
        MultipartFile newProfilePic = new MockMultipartFile("image", "rafy.jpg", "image/jpg", "rafy.jpg".getBytes());
        user.setPictureURL("pictureURL");
        //when
        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        //then
        assertNotNull(userSettingsService.changeProfilePic(newProfilePic));
    }

    @Test
    void removeProfilePicTest() {
        //given
        User user = new User();
        user.setPictureURL("pictureURL");
        //when
        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        //then
        assertNotNull(userSettingsService.removeProfilePic());
    }

    @Test
    void modifyInterestsTest() {
        //given
        User user = new User();
        List<Long> interests = List.of(1L, 2L, 3L);
        String newInterestsJson = "[1,2,3]";
        //when
        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.getAllInterests(1L)).thenReturn(interests);
        when(handleListJson.getInterests(newInterestsJson)).thenReturn(interests);
        String newInterests = "[1,2,3]";
        //then
        assertNotNull(userSettingsService.modifyInterests(newInterests));
    }

    @Test
    void getUserTest() {
        //given
        User user = new User();
        user.setId(1L);
        //when
        when(userService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        //then
        assertEquals(user.getId() , userSettingsService.getUser().getId());
    }


    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

}
