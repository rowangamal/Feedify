package com.example.backend.useCase;

import com.example.backend.dtos.EmailDTO;
import com.example.backend.dtos.OTPValidationDTO;
import com.example.backend.dtos.ResetPasswordDTO;
import com.example.backend.entities.User;
import com.example.backend.enums.VerificationResults;
import com.example.backend.exceptions.*;
import com.example.backend.services.OTPService;
import com.example.backend.services.SendEmailService;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class ResetPasswordUseCase {
    @Autowired
    private  UserService userService;

    @Autowired
    private  SendEmailService sendEmailService;

    @Autowired
    private  OTPService otpService;

    public void requestOTP(EmailDTO email) {
        try {
            User user = userService.getUserByEmail(email.getEmail());

            if(user.getPassword() == null) throw new EmailSignedUpWithGoogleException();

            String otp = otpService.saveResetPasswordOTP(user);
            sendEmailService.sendResetPasswordOTPEmail(email.getEmail(), otp);
        } catch (IOException e) {
            throw new ServiceUnavailableException();
        }
    }

    public void checkOTP(OTPValidationDTO otpValidationDTO) {
        User user = userService.getUserByEmail(otpValidationDTO.getEmail());
        VerificationResults result = otpService.validateOTP(user, otpValidationDTO.getOtp());

        if (result == VerificationResults.CODE_INCORRECT) throw new InvalidOtpException();
        if (result == VerificationResults.CODE_EXPIRED) throw new OtpExpiredException();
    }

    public void changePassword(ResetPasswordDTO resetPasswordDTO) {
        User user = userService.getUserByEmail(resetPasswordDTO.getEmail());
        userService.updatePassword(user, resetPasswordDTO.getNewPassword());
    }
}
