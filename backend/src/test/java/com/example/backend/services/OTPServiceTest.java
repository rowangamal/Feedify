package com.example.backend.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.example.backend.entities.User;
import com.example.backend.enums.VerificationResults;
import com.example.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

public class OTPServiceTest {

    @InjectMocks
    private OTPService otpService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecureRandom secureRandom;

    @Mock
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void generateOTPReturnsValidOtp() {
        String expectedOtp = "12345";

        when(secureRandom.nextInt(89999)).thenReturn(2345);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedOtp");
        when(userRepository.save(any(User.class))).thenReturn(user);
        String otp = otpService.generateOTP(user);

        assertNotNull(otp);
        assertEquals(expectedOtp, otp);
        verify(userRepository).save(user);
        verify(user).setResetPasswordOtp("encodedOtp");
        verify(user).setResetOtpExpiration(any(Timestamp.class));
    }

    @Test
    public void validateOTPWhenCodeExpiredReturnsCodeExpired() {
        String otp = "12345";
        LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(1);

        when(user.getResetOtpExpiration()).thenReturn(Timestamp.valueOf(expiredTime));
        when(passwordEncoder.matches(otp, user.getResetPasswordOtp())).thenReturn(true);
        VerificationResults result = otpService.validateOTP(user, otp);

        assertEquals(VerificationResults.CODE_EXPIRED, result);
        verify(user, never()).setResetPasswordOtp(null);
    }

    @Test
    public void validateOTPWhenCodeIncorrectReturnsCodeIncorrect() {
        String otp = "12345";
        LocalDateTime validTime = LocalDateTime.now().plusMinutes(1);

        when(user.getResetOtpExpiration()).thenReturn(Timestamp.valueOf(validTime));
        when(passwordEncoder.matches(otp, user.getResetPasswordOtp())).thenReturn(false);
        VerificationResults result = otpService.validateOTP(user, otp);

        assertEquals(VerificationResults.CODE_INCORRECT, result);
        verify(user, never()).setResetPasswordOtp(null);
    }

    @Test
    public void validateOTPWhenCodeValidReturnsSuccess() {
        String otp = "12345";
        LocalDateTime validTime = LocalDateTime.now().plusMinutes(10);

        when(user.getResetOtpExpiration()).thenReturn(Timestamp.valueOf(validTime));
        when(passwordEncoder.matches(otp, user.getResetPasswordOtp())).thenReturn(true);
        VerificationResults result = otpService.validateOTP(user, otp);

        assertEquals(VerificationResults.SUCCESS, result);
        verify(user).setResetPasswordOtp(null);
        verify(userRepository).save(user);
    }
}
