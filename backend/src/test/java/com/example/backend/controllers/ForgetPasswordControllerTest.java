package com.example.backend.controllers;

import com.example.backend.exceptions.*;
import com.example.backend.useCase.ResetPasswordUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ForgetPasswordControllerTest {
    private MockMvc mockMvc;
    @Mock
    private ResetPasswordUseCase resetPasswordUseCase;

    @InjectMocks
    private ForgetPasswordController forgetPasswordController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(forgetPasswordController).build();
    }

    @Test
    public void resetPasswordNormalReturnsOk() throws Exception {
        String emailJson = "{\"email\": \"test@example.com\"}";

        Mockito.doNothing().when(resetPasswordUseCase).requestOTP(Mockito.any());

        mockMvc.perform(post("/request-password-reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("OTP sent successfully to your email. Please check your inbox."));
    }

    @Test
    public void resetPasswordUserRegisteredWithGmailReturnsBadRequest() throws Exception {
        String emailJson = "{\"email\": \"notfound@example.com\"}";

        Mockito.doThrow(new EmailSignedUpWithGoogleException())
                .when(resetPasswordUseCase).requestOTP(Mockito.any());

        mockMvc.perform(post("/request-password-reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailJson))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("This Email is signed up with Gmail and does not need to reset password, try to login with this Gmail using login with google."));
    }

    @Test
    public void resetPasswordMailingServiceNotAvailableReturnsServiceUnavailable() throws Exception {
        String emailJson = "{\"email\": \"notfound@example.com\"}";

        Mockito.doThrow(new ServiceUnavailableException())
                .when(resetPasswordUseCase).requestOTP(Mockito.any());

        mockMvc.perform(post("/request-password-reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailJson))
                .andExpect(status().isServiceUnavailable())
                .andExpect(MockMvcResultMatchers.content().string("Service is not available right now, please try again later."));
    }

    @Test
    public void resetPasswordUserNotFoundReturnsNotFound() throws Exception {
        String emailJson = "{\"email\": \"notfound@example.com\"}";

        Mockito.doThrow(new UserNotFoundException("Email not found"))
                .when(resetPasswordUseCase).requestOTP(Mockito.any());

        mockMvc.perform(post("/request-password-reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailJson))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Email is not registered in the system."));
    }

    @Test
    public void verifyOtpNormalReturnsOk() throws Exception {
        String otpJson = "{\"email\": \"test@example.com\", \"otp\": \"123456\"}";

        Mockito.doNothing().when(resetPasswordUseCase).checkOTP(Mockito.any());

        mockMvc.perform(post("/verify-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(otpJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("OTP is correct."));
    }

    @Test
    public void verifyOtpInvalidReturnsUnauthorized() throws Exception {
        String otpJson = "{\"email\": \"test@example.com\", \"otp\": \"12345\"}";

        Mockito.doThrow(new InvalidOtpException())
                .when(resetPasswordUseCase).checkOTP(Mockito.any());

        mockMvc.perform(post("/verify-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(otpJson))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("The OTP you have entered is not correct for the provided email, please try another one."));
    }

    @Test
    public void verifyOtpExpiredReturnsUnauthorized() throws Exception {
        String otpJson = "{\"email\": \"test@example.com\", \"otp\": \"12345\"}";

        Mockito.doThrow(new OtpExpiredException())
                .when(resetPasswordUseCase).checkOTP(Mockito.any());

        mockMvc.perform(post("/verify-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(otpJson))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("The OTP you have entered has Expired, please try again later."));
    }

    @Test
    public void verifyOtpUserNotFoundReturnsReturnsNotFound() throws Exception {
        String otpJson = "{\"email\": \"test@example.com\", \"otp\": \"wrongotp\"}";

        Mockito.doThrow(new UserNotFoundException("User not found"))
                .when(resetPasswordUseCase).checkOTP(Mockito.any());

        mockMvc.perform(post("/verify-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(otpJson))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Email is not registered in the system."));
    }

    @Test
    public void changePasswordNormalReturnsOk() throws Exception {
        String resetPasswordJson = "{\"email\": \"test@example.com\", \"newPassword\": \"newpassword\"}";

        Mockito.doNothing().when(resetPasswordUseCase).changePassword(Mockito.any());

        mockMvc.perform(post("/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resetPasswordJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Password changed successfully."));
    }

    @Test
    public void changePasswordUserNotFoundReturnsNotFound() throws Exception {
        String resetPasswordJson = "{\"email\": \"notfound@example.com\", \"newPassword\": \"newpassword\"}";

        Mockito.doThrow(new UserNotFoundException("User not found"))
                .when(resetPasswordUseCase).changePassword(Mockito.any());

        mockMvc.perform(post("/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resetPasswordJson))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Email is not registered in the system."));
    }
}
