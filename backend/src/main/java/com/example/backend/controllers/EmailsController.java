package com.example.backend.controllers;

import com.example.backend.dtos.ApiResponseDTO;
import com.example.backend.dtos.EmailDTO;
import com.example.backend.dtos.OTPValidationDTO;
import com.example.backend.dtos.ResetPasswordDTO;
import com.example.backend.usecase.ResetPasswordUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailsController {
    private final ResetPasswordUseCase resetPasswordUseCase;

    @PostMapping("/request-password-reset")
    public ApiResponseDTO resetPassword(@RequestBody @Valid EmailDTO email) {
        return resetPasswordUseCase.requestOTP(email);
    }

    @PostMapping("/verify-otp")
    public ApiResponseDTO verifyOtp(@RequestBody @Valid OTPValidationDTO otpValidationDTO){
        return resetPasswordUseCase.checkOTP(otpValidationDTO);
    }

    @PostMapping("/change-password")
    public ApiResponseDTO changePassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO) {
        return resetPasswordUseCase.changePassword(resetPasswordDTO);
    }
}
