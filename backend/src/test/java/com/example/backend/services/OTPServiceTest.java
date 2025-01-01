package com.example.backend.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import com.example.backend.entities.Otp;
import com.example.backend.entities.User;
import com.example.backend.enums.VerificationResults;
import com.example.backend.repositories.OtpRepository;
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
    private OtpRepository otpRepository;

    @Mock
    private SecureRandom secureRandom;

    @Mock
    private User user;

    @Mock
    private Otp otp;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void generateOTPReturnsValidOtp() {
        final String EXPECTED_OTP = "12345";
        final int OTP_RANDOM_BOUND = 89999;
        final int MOCK_RANDOM_OTP = 2345;
        final String ENCODED_OTP = "encodedOtp";
        when(secureRandom.nextInt(OTP_RANDOM_BOUND)).thenReturn(MOCK_RANDOM_OTP);
        when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_OTP);

        String otpValue = otpService.generateOTP(user);

        assertNotNull(otpValue);
        assertEquals(EXPECTED_OTP, otpValue);
    }


    @Test
    public void validateOTPWhenCodeExpiredReturnsCodeExpired() {
        String otpValue = "12345";
        LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(1);
        when(otpRepository.findUserById(user.getId())).thenReturn(Optional.of(otp));
        when(otp.getResetOtpExpiration()).thenReturn(Timestamp.valueOf(expiredTime));
        when(passwordEncoder.matches(otpValue, otp.getResetPasswordOtp())).thenReturn(true);

        when(otp.getResetOtpExpiration()).thenReturn(Timestamp.valueOf(expiredTime));
        when(passwordEncoder.matches(otpValue, otp.getResetPasswordOtp())).thenReturn(true);
        VerificationResults result = otpService.validateForgetPasswordOTP(user, otpValue);

        assertEquals(VerificationResults.CODE_EXPIRED, result);
        verify(otp, never()).setResetPasswordOtp(null);
    }

    @Test
    public void validateOTPWhenCodeIncorrectReturnsCodeIncorrect() {
        String otpValue = "12345";
        LocalDateTime validTime = LocalDateTime.now().plusMinutes(1);
        when(otpRepository.findUserById(user.getId())).thenReturn(Optional.of(otp));
        when(otp.getResetOtpExpiration()).thenReturn(Timestamp.valueOf(validTime));
        when(passwordEncoder.matches(otpValue, otp.getResetPasswordOtp())).thenReturn(false);

        when(otp.getResetOtpExpiration()).thenReturn(Timestamp.valueOf(validTime));
        when(passwordEncoder.matches(otpValue, otp.getResetPasswordOtp())).thenReturn(false);
        VerificationResults result = otpService.validateForgetPasswordOTP(user, otpValue);

        assertEquals(VerificationResults.CODE_INCORRECT, result);
        verify(otp, never()).setResetPasswordOtp(null);
    }

    @Test
    public void validateOTPWhenCodeValidReturnsSuccess() {
        String otpValue = "12345";
        LocalDateTime validTime = LocalDateTime.now().plusMinutes(10);
        otp.setResetOtpExpiration(Timestamp.valueOf(validTime));
        otp.setResetPasswordOtp("encodedOtp");
        when(otp.getResetOtpExpiration()).thenReturn(Timestamp.valueOf(validTime));
        when(otpRepository.findUserById(user.getId())).thenReturn(Optional.of(otp));
        when(passwordEncoder.matches(otpValue, otp.getResetPasswordOtp())).thenReturn(true);

        when(otp.getResetOtpExpiration()).thenReturn(Timestamp.valueOf(validTime));
        when(passwordEncoder.matches(otpValue, otp.getResetPasswordOtp())).thenReturn(true);
        VerificationResults result = otpService.validateForgetPasswordOTP(user, otpValue);

        assertEquals(VerificationResults.SUCCESS, result);
        verify(otp).setResetPasswordOtp(null);
        verify(otpRepository).save(otp);
    }
}
