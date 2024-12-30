package com.example.backend.services;

import com.example.backend.entities.Otp;
import com.example.backend.entities.User;
import com.example.backend.enums.VerificationResults;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OTPService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private SecureRandom secureRandom;

    public String generateOTP(User user) {
        String otp_value = String.valueOf(10000 + secureRandom.nextInt(89999));
        Otp otp = otpRepository.findUserById(user.getId())
                .orElseGet(() -> {
                    Otp newUserOtp = new Otp();
                    newUserOtp.setUser(user);
                    return newUserOtp;
                });
        otp.setResetPasswordOtp(passwordEncoder.encode(otp_value));
        otp.setResetOtpExpiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15))); // valid for 15 mins
        otpRepository.save(otp);
        return otp_value;
    }

    public VerificationResults validateOTP(User user, String otp_value) {
        Optional<Otp> otpOptional = otpRepository.findUserById(user.getId());
        if(otpOptional.isPresent()) {
            Otp otp = otpOptional.get();
            if (LocalDateTime.now().isAfter(otp.getResetOtpExpiration().toLocalDateTime())) {
                return VerificationResults.CODE_EXPIRED;
            }

            boolean isValidOtp = passwordEncoder.matches(otp_value, otp.getResetPasswordOtp());
            if (isValidOtp) {
                otp.setResetPasswordOtp(null);
                otpRepository.save(otp);
                return VerificationResults.SUCCESS;
            }

            return VerificationResults.CODE_INCORRECT;
        } else {
            throw new UserNotFoundException("User Not Found");
        }

    }
}