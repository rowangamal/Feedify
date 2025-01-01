package com.example.backend.services;

import com.example.backend.dtos.OTPValidationDTO;
import com.example.backend.exceptions.InvalidOtpException;
import com.example.backend.exceptions.OtpExpiredException;
import com.example.backend.repositories.UserRepository;
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

    @Autowired
    private UserService userService;

    public String generateOTP(User user) {
   // here
        return String.valueOf(10000 + secureRandom.nextInt(89999));
    }

    public String saveResetPasswordOTP(User user) {
        String otp = generateOTP(user);
        user.setResetPasswordOtp(passwordEncoder.encode(otp));
        user.setResetOtpExpiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15))); // valid for 15 mins
        userRepository.save(user);
        return otp;
    }

    public String saveVerificationCodeOTP(User user) {
        String otp = generateOTP(user);
        user.setVerificationCode(passwordEncoder.encode(otp));
        user.setCodeExpirationDate(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15))); // valid for 15 mins
        userRepository.save(user);
        return otp;
    }

    public VerificationResults validateForgetPasswordOTP(User user, String otp) {
        if (LocalDateTime.now().isAfter(user.getResetOtpExpiration().toLocalDateTime())) {
            return VerificationResults.CODE_EXPIRED;
        }
// here
        final int OTP_EXPIRATION_TIMESTAMP = 15;
        final int BASE_OTP_VALUE = 10000;
        final int OTP_BOUND_VALUE = 89999;

        String otp_value = String.valueOf(BASE_OTP_VALUE + secureRandom.nextInt(OTP_BOUND_VALUE));
        Otp otp = otpRepository.findUserById(user.getId())
                .orElseGet(() -> {
                    Otp newUserOtp = new Otp();
                    newUserOtp.setUser(user);
                    return newUserOtp;
                });
        otp.setResetPasswordOtp(passwordEncoder.encode(otp_value));
        otp.setResetOtpExpiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(OTP_EXPIRATION_TIMESTAMP)));
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
// here
            return VerificationResults.CODE_INCORRECT;
        } else {
            throw new UserNotFoundException("User Not Found");
        }

    }

    private VerificationResults validateSignupOTP(User user, String otp) {
        if (LocalDateTime.now().isAfter(user.getCodeExpirationDate().toLocalDateTime())) {
            return VerificationResults.CODE_EXPIRED;
        }

        boolean isValidOtp = passwordEncoder.matches(otp, user.getVerificationCode());
        if (isValidOtp) {
            user.setResetPasswordOtp(null);
            userRepository.save(user);
            return VerificationResults.SUCCESS;
        }

        return VerificationResults.CODE_INCORRECT;
    }

    public void checkSignUpOTP(OTPValidationDTO otpValidationDTO) {
        User user = userService.getUserByEmail(otpValidationDTO.getEmail());
        VerificationResults result = validateSignupOTP(user, otpValidationDTO.getOtp());

        if (result == VerificationResults.CODE_INCORRECT) throw new InvalidOtpException();
        if (result == VerificationResults.CODE_EXPIRED) throw new OtpExpiredException();
        user.setIsVerified(true);
        userRepository.save(user);
    }

}