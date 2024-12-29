package com.example.backend.controllers;

import com.example.backend.dtos.EmailDTO;
import com.example.backend.dtos.OTPValidationDTO;
import com.example.backend.dtos.ResetPasswordDTO;
import com.example.backend.exceptions.*;
import com.example.backend.useCase.ResetPasswordUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgetPasswordController {
    @Autowired
    private  ResetPasswordUseCase resetPasswordUseCase;

    @PostMapping("/request-password-reset")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid EmailDTO email) {
        try {
            resetPasswordUseCase.requestOTP(email);
            return ResponseEntity.ok("OTP sent successfully to your email. Please check your inbox.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email is not registered in the system.");
        } catch (ServiceUnavailableException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (EmailSignedUpWithGoogleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody @Valid OTPValidationDTO otpValidationDTO){
        try {
            resetPasswordUseCase.checkOTP(otpValidationDTO);
            return ResponseEntity.ok("OTP is correct.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email is not registered in the system.");
        } catch (InvalidOtpException | OtpExpiredException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO) {
        try {
            resetPasswordUseCase.changePassword(resetPasswordDTO);
            return ResponseEntity.ok("Password changed successfully.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email is not registered in the system.");
        }
    }
}
