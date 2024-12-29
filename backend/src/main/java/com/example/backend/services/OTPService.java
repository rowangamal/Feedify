package com.example.backend.services;

import com.example.backend.entities.User;
import com.example.backend.enums.VerificationResults;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class OTPService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecureRandom secureRandom;

    public String generateOTP(User user) {
        String otp = String.valueOf(10000 + secureRandom.nextInt(89999));
        user.setResetPasswordOtp(passwordEncoder.encode(otp));
        user.setResetOtpExpiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15))); // valid for 15 mins
        userRepository.save(user);
        return otp;
    }

    public VerificationResults validateOTP(User user, String otp) {
        if (LocalDateTime.now().isAfter(user.getResetOtpExpiration().toLocalDateTime())) {
            return VerificationResults.CODE_EXPIRED;
        }

        boolean isValidOtp = passwordEncoder.matches(otp, user.getResetPasswordOtp());
        if (isValidOtp) {
            user.setResetPasswordOtp(null);
            userRepository.save(user);
            return VerificationResults.SUCCESS;
        }

        return VerificationResults.CODE_INCORRECT;
    }
}