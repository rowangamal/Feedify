package com.example.backend.controllers;

import com.example.backend.services.SignupService;
import com.example.backend.exceptions.UserAlreadyExistException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SignupControllerTest {

    private AutoCloseable mocks;
    private MockMvc mockMvc;

    @InjectMocks
    private SignupController signupController;

    @Mock
    private SignupService signupService;

    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(signupController).build();
    }

    @Test
    void signupWithValidDataReturnsCreated() throws Exception {
        String validUserJson = """
            {
                "firstName": "Armia",
                "lastName": "Joseph",
                "username": "E43eia",
                "gender": true,
                "dateOfBirth": "2024-12-01",
                "email": "armia.joseph35@gmail.com",
                "password": "Password@1234"
            }
        """;

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUserJson))
                .andExpect(status().isCreated());
    }

    @Test
    void signupWithExistingUsernameReturnsConflict() throws Exception {
        String existingUserJson = """
            {
                "firstName": "Armia",
                "lastName": "Joseph",
                "username": "existingUser",
                "gender": true,
                "dateOfBirth": "2024-12-01",
                "email": "armia.joseph35@gmail.com",
                "password": "Password@1234"
            }
        """;

        doThrow(new UserAlreadyExistException("Username already exists")).when(signupService).signup(any());

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(existingUserJson))
                .andExpect(status().isConflict());
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }
}