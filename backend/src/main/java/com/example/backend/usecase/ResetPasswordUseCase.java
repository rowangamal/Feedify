package com.example.backend.usecase;

import com.example.backend.dto.EmailDTO;
import com.example.backend.entities.User;
import com.example.backend.services.OTPService;
import com.example.backend.services.SendEmailService;
import com.example.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ResetPasswordUseCase {
    private final UserService userService;
    private final SendEmailService sendEmailService;
    private final OTPService otpService;

    public void requestOTP(EmailDTO email) throws IOException {
        User user = userService.getUserByEmail(email.getEmail());
        String otp = otpService.generateOTP(user);
        sendEmailService.sendResetPasswordOTPEmail(email.getEmail(), otp);
    }

//    public void checkOTP(OTPValidationDTO otpValidationDTO) {
//    }
//
//    public void changePassword(ChangePasswordDTO changePasswordDTO) {
//    }
}
