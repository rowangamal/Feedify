package com.example.backend.controllers;
import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.dtos.UserLoginDTO;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.services.LoginService;
import com.example.backend.services.SignupService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GoogleAuthControllerTest {

    private AutoCloseable mocks;
    private MockMvc mockMvc;

    @InjectMocks
    private GoogleAuthController googleAuthController;

    @Mock
    private SignupService signupService;

    @Mock
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(googleAuthController).build();
    }

    // Invalid JSON format
    @Test
    void signupWithInvalidJsonReturnsBadRequest() throws Exception {
        String invalidGoogleUserJson = """
                {
                    "firstName": "google",
                    "lastName": "user",
                    "email": "amin@gmail.com"
                """;  // Missing closing brace to make it invalid

        mockMvc.perform(post("/api/auth/signupGoogle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidGoogleUserJson))
                .andExpect(status().isBadRequest());
    }

    //  Missing required fields
    @Test
    void signupWithMissingRequiredFieldsReturnsBadRequest() throws Exception {
        String missingFieldGoogleUserJson = """
                {
                    "firstName": "google",
                    "lastName": "user"
                    // Missing email field
                }
                """;

        mockMvc.perform(post("/api/auth/signupGoogle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(missingFieldGoogleUserJson))
                .andExpect(status().isBadRequest());
    }

    // Test case 5: Invalid email format
    @Test
    void signupWithInvalidEmailFormatReturnsBadRequest() throws Exception {
        String invalidEmailGoogleUserJson = """
                {
                    "firstName": "google",
                    "lastName": "user",
                    "email": "amin@gmail"  // Invalid email format
                }
                """;

        mockMvc.perform(post("/api/auth/signupGoogle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidEmailGoogleUserJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signinWithInvalidEmailFormatReturnsBadRequest() throws Exception {
        String invalidEmailGoogleUserJson = """
                {
                    "email": "amin@gmail"  // Invalid email format
                }
                """;

        mockMvc.perform(post("/api/auth/loginGoogle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidEmailGoogleUserJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    void loginWithValidGoogleDataReturnsOk() throws Exception {
        String validGoogleUserJson = """
                {
                    "email": "amin@gmail.com"
                }
                """;

        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setJWTToken("someJWTToken");
        authUserInfo.setUserId(1L);
        authUserInfo.setUsername("Amin Mohamed");
        authUserInfo.setIsAdmin(false);

        when(loginService.verifyGoogleAuth(any(UserLoginDTO.class))).thenReturn(authUserInfo);

        mockMvc.perform(post("/api/auth/loginGoogle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validGoogleUserJson))
                .andExpect(status().isOk());

        verify(loginService).verifyGoogleAuth(any());
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }
}
