package com.example.backend.useCases;

import com.example.backend.dtos.EmailDTO;
import com.example.backend.dtos.OTPValidationDTO;
import com.example.backend.dtos.ResetPasswordDTO;
import com.example.backend.entities.User;
import com.example.backend.enums.VerificationResults;
import com.example.backend.exceptions.*;
import com.example.backend.services.OTPService;
import com.example.backend.services.SendEmailService;
import com.example.backend.services.UserService;
import com.example.backend.useCase.ResetPasswordUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class ResetPasswordUseCaseTest {

    @InjectMocks
    private ResetPasswordUseCase resetPasswordUseCase;

    @Mock
    private UserService userService;

    @Mock
    private SendEmailService sendEmailService;

    @Mock
    private OTPService otpService;

    private User user;
    private EmailDTO emailDTO;
    private OTPValidationDTO otpValidationDTO;
    private ResetPasswordDTO resetPasswordDTO;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        emailDTO = new EmailDTO();
        emailDTO.setEmail("test@example.com");

        otpValidationDTO = new OTPValidationDTO();
        otpValidationDTO.setEmail("test@example.com");
        otpValidationDTO.setOtp("123456");

        resetPasswordDTO = new ResetPasswordDTO();
        resetPasswordDTO.setEmail("test@example.com");
        resetPasswordDTO.setNewPassword("newPassword123");
    }

    @Test
    public void requestOTP_ShouldSendOtp_WhenUserExistsAndPasswordNotNull() throws IOException {
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(otpService.generateOTP(user)).thenReturn("123456");
        resetPasswordUseCase.requestOTP(emailDTO);

        verify(sendEmailService, times(1)).sendResetPasswordOTPEmail(anyString(), eq("123456"));
    }

    @Test
    public void requestOTP_ShouldThrowEmailSignedUpWithGoogleException_WhenPasswordIsNull() {
        user.setPassword(null);
        when(userService.getUserByEmail(anyString())).thenReturn(user);

        assertThrows(EmailSignedUpWithGoogleException.class, () -> resetPasswordUseCase.requestOTP(emailDTO));
    }

    @Test
    public void requestOTP_ShouldThrowServiceUnavailableException_WhenIOExceptionOccurs() {
        when(userService.getUserByEmail(anyString())).thenThrow(new IOException());

        assertThrows(ServiceUnavailableException.class, () -> resetPasswordUseCase.requestOTP(emailDTO));
    }

    @Test
    public void checkOTP_ShouldThrowInvalidOtpException_WhenOtpIsIncorrect() {
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(otpService.validateOTP(user, otpValidationDTO.getOtp())).thenReturn(VerificationResults.CODE_INCORRECT);

        assertThrows(InvalidOtpException.class, () -> resetPasswordUseCase.checkOTP(otpValidationDTO));
    }

    @Test
    public void checkOTP_ShouldThrowOtpExpiredException_WhenOtpIsExpired() {
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(otpService.validateOTP(user, otpValidationDTO.getOtp())).thenReturn(VerificationResults.CODE_EXPIRED);

        assertThrows(OtpExpiredException.class, () -> resetPasswordUseCase.checkOTP(otpValidationDTO));
    }

    @Test
    public void checkOTP_ShouldNotThrowException_WhenOtpIsCorrect() {
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(otpService.validateOTP(user, otpValidationDTO.getOtp())).thenReturn(VerificationResults.SUCCESS);

        assertDoesNotThrow(() -> resetPasswordUseCase.checkOTP(otpValidationDTO));
    }

    @Test
    public void changePassword_ShouldUpdatePassword_WhenUserExists() {
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        resetPasswordUseCase.changePassword(resetPasswordDTO);

        verify(userService, times(1)).updatePassword(user, resetPasswordDTO.getNewPassword());
    }

    @Test
    public void changePassword_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        when(userService.getUserByEmail(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> resetPasswordUseCase.changePassword(resetPasswordDTO));
    }
}
