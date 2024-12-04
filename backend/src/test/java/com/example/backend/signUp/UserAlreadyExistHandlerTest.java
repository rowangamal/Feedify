package com.example.backend.signUp;

import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.exceptions.UsernameTakenException;
import com.example.backend.services.UserService;
import com.example.backend.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserAlreadyExistHandlerTest {

    private UserAlreadyExistHandler userAlreadyExistHandler;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        userAlreadyExistHandler = new UserAlreadyExistHandler(userService);
    }

    @Test
    void emailNotTakenDoesNotThrowException() {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setEmail("new_user@example.com");

        when(userService.getUserByEmail("new_user@example.com")).thenThrow(new UserNotFoundException("User not found"));

        assertDoesNotThrow(() -> userAlreadyExistHandler.handleRequest(userSignupDTO));
    }


    @Test
    void emptyEmailThrowsUserAlreadyExistException() {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setEmail("");

        assertThrows(UserAlreadyExistException.class, () -> userAlreadyExistHandler.handleRequest(userSignupDTO));
    }
}