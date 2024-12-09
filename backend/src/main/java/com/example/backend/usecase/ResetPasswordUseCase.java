package com.example.backend.usecase;

import com.example.backend.dto.ApiResponseDTO;
import com.example.backend.dto.EmailDTO;
import com.example.backend.dto.OTPValidationDTO;
import com.example.backend.dto.ResetPasswordDTO;
import com.example.backend.entities.User;
import com.example.backend.enums.VerificationResults;
import com.example.backend.exceptions.UserNotFoundException;
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

    public ApiResponseDTO requestOTP(EmailDTO email) {
        ApiResponseDTO response = new ApiResponseDTO();
        try {
            User user = userService.getUserByEmail(email.getEmail());
            String otp = otpService.generateOTP(user);

            try {
                sendEmailService.sendResetPasswordOTPEmail(email.getEmail(), otp);
            } catch (IOException e) {
                response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
                return response;
            }

            response.setMessage("OTP sent successfully to your email. Please check your inbox.");
            response.setStatus(HttpStatus.OK.value());
            return response;
        } catch (UserNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return response;
        }

    }

    public ApiResponseDTO checkOTP(OTPValidationDTO otpValidationDTO) {
        ApiResponseDTO response = new ApiResponseDTO();
        try {
            User user = userService.getUserByEmail(otpValidationDTO.getEmail());

            VerificationResults result = otpService.validateOTP(user, otpValidationDTO.getOtp());
            if (result == VerificationResults.CODE_INCORRECT) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return response;
            }

            response.setStatus(HttpStatus.OK.value());
            return response;
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return response;
        }
    }

    public ApiResponseDTO changePassword(ResetPasswordDTO resetPasswordDTO) {
        ApiResponseDTO response = new ApiResponseDTO();
        try {
            User user = userService.getUserByEmail(resetPasswordDTO.getEmail());
            // Update the user's password
            userService.updatePassword(user, resetPasswordDTO.getNewPassword());
            response.setMessage("Password changed successfully.");
            response.setStatus(HttpStatus.OK.value());
            return response;
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return response;
        }
    }
}
