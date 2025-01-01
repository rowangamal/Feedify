package com.example.backend.services;

import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.dtos.UserLoginDTO;
import com.example.backend.entities.User;
import com.example.backend.enums.Role;
import com.example.backend.exceptions.InvalidCredentialsException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.exceptions.UserNotVerifiedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoginServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    private User testUser(){
        User user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setEmail("test");
        user.setIsVerified(true);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode("test"));
        return user;
    }
    @Test
    void verifyCorrectUser() {
        User user = testUser();
        when(userService.getUserByEmail("test")).thenReturn(user);
        when(userService.getUserRole(user)).thenReturn(Role.valueOf("USER"));
        when(jwtService.generateToken(new AuthUserInfo())).thenReturn("token");
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("test");
        userLoginDTO.setPassword("test");
        AuthUserInfo authUserInfo1 = new AuthUserInfo();
        authUserInfo1.setIsAdmin(false);
        authUserInfo1.setUserId(1L);
        authUserInfo1.setUsername("test");
        assertEquals(authUserInfo1.getUserId(), loginService.verify(userLoginDTO).getUserId());
        assertEquals(authUserInfo1.getUsername(), loginService.verify(userLoginDTO).getUsername());
        assertEquals(authUserInfo1.getIsAdmin(), loginService.verify(userLoginDTO).getIsAdmin());
    }

    @Test
    void verifyNotVerifiedUser() {
        User user = testUser();
        user.setIsVerified(false);
        when(userService.getUserByEmail("test")).thenReturn(user);
        when(userService.getUserRole(user)).thenReturn(Role.valueOf("USER"));
        when(jwtService.generateToken(new AuthUserInfo())).thenReturn("token");
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("test");
        userLoginDTO.setPassword("test");
        AuthUserInfo authUserInfo1 = new AuthUserInfo();
        authUserInfo1.setIsAdmin(false);
        authUserInfo1.setUserId(1L);
        authUserInfo1.setUsername("test");
        assertThrows(UserNotVerifiedException.class, () -> loginService.verify(userLoginDTO));
    }

    @Test
    void verifyWrongEmail() {
        when(userService.getUserByEmail("test")).thenThrow(UserNotFoundException.class);
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("test");
        userLoginDTO.setPassword("test");
        assertThrows(Exception.class, () -> loginService.verify(userLoginDTO));
        assertThrows(UserNotFoundException.class, () -> loginService.verify(userLoginDTO));
    }
    @Test
    void verifyWrongPassword() {
        User user = testUser();
        when(userService.getUserByEmail("test")).thenReturn(user);
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("test");
        userLoginDTO.setPassword("wrong_password");
        assertThrows(Exception.class, () -> loginService.verify(userLoginDTO));
        assertThrows(InvalidCredentialsException.class, () -> loginService.verify(userLoginDTO));
    }

    @Test
    void verifyNullEmail() {
        when(userService.getUserByEmail("test")).thenReturn(null);
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(null);
        userLoginDTO.setPassword("test");
        assertThrows(Exception.class, () -> loginService.verify(userLoginDTO));
        assertThrows(NullPointerException.class, () -> loginService.verify(userLoginDTO));
    }

    @Test
    void verifyNullPassword() {
        User user = testUser();
        when(userService.getUserByEmail("test")).thenReturn(null);
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("test");
        userLoginDTO.setPassword(null);
        assertThrows(Exception.class, () -> loginService.verify(userLoginDTO));
        assertThrows(NullPointerException.class, () -> loginService.verify(userLoginDTO));
    }

}