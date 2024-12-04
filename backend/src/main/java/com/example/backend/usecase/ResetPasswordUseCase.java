package com.example.backend.usecase;

import com.example.backend.dto.ApiResponseDTO;
import com.example.backend.dto.EmailDTO;
import com.example.backend.dto.OTPValidationDTO;
import com.example.backend.entities.User;
import com.example.backend.enums.VerificationResults;
import com.example.backend.exceptions.OTPValidationException;
import com.example.backend.services.OTPService;
import com.example.backend.services.SendEmailService;
import com.example.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ResetPasswordUseCase {
    private final UserService userService;
    private final SendEmailService sendEmailService;
    private final OTPService otpService;

    public ApiResponseDTO requestOTP(EmailDTO email) throws IOException {
        User user = userService.getUserByEmail(email.getEmail());
        String otp = otpService.generateOTP(user);
        sendEmailService.sendResetPasswordOTPEmail(email.getEmail(), otp);
        ApiResponseDTO response = new ApiResponseDTO();
        response.setMessage("OTP sent successfully to your email. Please check your inbox.");
        response.setStatus(HttpStatus.OK.value());
        return response;
    }

    public void checkOTP(OTPValidationDTO otpValidationDTO) {
        User user = userService.getUserByEmail(otpValidationDTO.getEmail());

        VerificationResults result = otpService.validateOTP(user, otpValidationDTO.getOtp());
        if (result == VerificationResults.CODE_INCORRECT) {
            throw new OTPValidationException();
        }
    }

    public void changePassword(String password) {
//        User user = userService.getCurrentUser();
//        // Update the user's password
//        userService.updatePassword(user, changePasswordDTO.getNewPassword());
//
//        ApiResponseDTO response = new ApiResponseDTO();
//        response.setMessage("Password changed successfully.");
//        response.setStatus(HttpStatus.OK.value());
//        return response;
    }
}
