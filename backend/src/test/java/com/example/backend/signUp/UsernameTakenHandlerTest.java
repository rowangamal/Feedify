package com.example.backend.signUp;

import com.example.backend.dtos.UserSignupDTO;
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

class UsernameTakenHandlerTest {

    private UsernameTakenHandler usernameTakenHandler;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        usernameTakenHandler = new UsernameTakenHandler(userService);
    }

    @Test
    void usernameNotTakenDoesNotThrowException() {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setUsername("new_user");

        // Mock behavior: simulate that the username does not exist
        when(userService.getUserByUsername("new_user")).thenThrow(new UserNotFoundException("User not found"));

        assertDoesNotThrow(() -> usernameTakenHandler.handleRequest(userSignupDTO));
    }

    @Test
    void usernameTakenThrowsUsernameTakenException() {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setUsername("existing_user");

        when(userService.getUserByUsername("existing_user")).thenReturn(new User());

        assertThrows(UsernameTakenException.class, () -> usernameTakenHandler.handleRequest(userSignupDTO));
    }

    @Test
    void nullUsernameThrowsUsernameTakenException() {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setUsername(null);

        assertThrows(UsernameTakenException.class, () -> usernameTakenHandler.handleRequest(userSignupDTO));
    }

    @Test
    void emptyUsernameThrowsUsernameTakenException() {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setUsername("");

        assertThrows(UsernameTakenException.class, () -> usernameTakenHandler.handleRequest(userSignupDTO));
    }
}