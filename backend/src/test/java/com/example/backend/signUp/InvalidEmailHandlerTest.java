package com.example.backend.signUp;

import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.exceptions.InvalidEmailException;
import com.example.backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class InvalidEmailHandlerTest {

    private InvalidEmailHandler invalidEmailHandler;

    @BeforeEach
    void setUp() {
        invalidEmailHandler = new InvalidEmailHandler();
    }

    @Test
    void validEmailDoesNotThrowException() {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setEmail("valid.email@gmail.com");

        invalidEmailHandler.handleRequest(userSignupDTO);
    }

    @Test
    void invalidEmailThrowsInvalidEmailException() {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setEmail("invalid.email");

        assertThrows(InvalidEmailException.class, () -> invalidEmailHandler.handleRequest(userSignupDTO));
    }

    @Test
    void nullEmailThrowsInvalidEmailException() {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setEmail(null);

        assertThrows(InvalidEmailException.class, () -> invalidEmailHandler.handleRequest(userSignupDTO));
    }

    @Test
    void emptyEmailThrowsInvalidEmailException() {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setEmail("");

        assertThrows(InvalidEmailException.class, () -> invalidEmailHandler.handleRequest(userSignupDTO));
    }
}