package com.example.backend.services;

import com.example.backend.entities.User;
import com.example.backend.enums.VerificationResults;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class OTPService {

    private final UserRepository userRepository;

    public String generateOTP(User user) {
        String otp = String.valueOf(10000 + new SecureRandom().nextInt(99999));
        user.setResetPasswordOtp(otp);
        userRepository.save(user);
        return otp;
    }

    public VerificationResults validateOTP(User user, String otp) {
        // TODO make expiration time

        // Validate OTP
        boolean isValidOtp = otp.equalsIgnoreCase(user.getResetPasswordOtp());
        if (isValidOtp) {
            user.setResetPasswordOtp(null); // clear OTP after successful validation
            userRepository.save(user);
            return VerificationResults.SUCCESS;
        }

        return VerificationResults.CODE_INCORRECT;
    }

}