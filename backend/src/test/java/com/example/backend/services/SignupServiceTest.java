package com.example.backend.services;

import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.entities.User;
import com.example.backend.exceptions.InvalidEmailException;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.exceptions.UsernameTakenException;
import com.example.backend.signUp.SignupHandler;
import com.example.backend.signUp.UsernameTakenHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SignupServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private SignupService signupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signupWithValidUser() {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setFirstName("Armia");
        userSignupDTO.setLastName("Joseph");
        userSignupDTO.setUsername("E43eia");
        userSignupDTO.setGender(true);
        userSignupDTO.setDateOfBirth(new Date(2024, 12, 1));
        userSignupDTO.setEmail("armia.joseeph35@gmail.com");
        userSignupDTO.setPassword("Password@1234");

        assertThrows(UsernameTakenException.class, () -> signupService.signup(userSignupDTO));
    }

    @Test
    void signupWithExistingUsernameThrowsException() {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setFirstName("Armia");
        userSignupDTO.setLastName("Joseph");
        userSignupDTO.setUsername("E43eia");
        userSignupDTO.setGender(true);
        userSignupDTO.setDateOfBirth(new Date(2024, 12, 1));
        userSignupDTO.setEmail("armia.joseeph35@gmail.com");
        userSignupDTO.setPassword("Password@1234");

        doNothing().when(userService).saveUser(any(User.class));

        doThrow(new UsernameTakenException("Username already exists")).when(userService).saveUser(any(User.class));

        assertThrows(UsernameTakenException.class, () -> signupService.signup(userSignupDTO));
    }

    @Test
    void signupWithExistingUserThrowsException() {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setFirstName("Armia");
        userSignupDTO.setLastName("Joseph");
        userSignupDTO.setUsername("E43eia");
        userSignupDTO.setGender(true);
        userSignupDTO.setDateOfBirth(new Date(2024, 12, 1));
        userSignupDTO.setEmail("armia.joseeph35@gmail.com");
        userSignupDTO.setPassword("Password@1234");
        doNothing().when(userService).saveUser(any(User.class));
        userSignupDTO.setUsername("new_unique_user");
        doThrow(new UsernameTakenException("Username already exists")).when(userService).saveUser(any(User.class));
        assertThrows(UsernameTakenException.class, () -> signupService.signup(userSignupDTO));
    }

//    @Test
//    void signupWithInvalidEmailThrowsException() {
//        UserSignupDTO userSignupDTO = new UserSignupDTO();
//        userSignupDTO.setFirstName("Armia");
//        userSignupDTO.setLastName("Joseph");
//        userSignupDTO.setUsername("new_user");
//        userSignupDTO.setGender(true);
//        userSignupDTO.setDateOfBirth(Date.valueOf("2024-12-01"));
//        userSignupDTO.setEmail("invalid.email");
//        userSignupDTO.setPassword("Password@1234");
//        doNothing().when(userService).saveUser(any(User.class));
//        assertThrows(InvalidEmailException.class, () -> signupService.signup(userSignupDTO));
//    }
}