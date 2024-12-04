package com.example.backend.controllers;

import com.example.backend.dto.ApiResponseDTO;
import com.example.backend.dto.EmailDTO;
import com.example.backend.dto.OTPValidationDTO;
import com.example.backend.usecase.ResetPasswordUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class EmailsController {
    private final ResetPasswordUseCase resetPasswordUseCase;

    @PostMapping("/request-password-reset")
    public ApiResponseDTO resetPassword(@RequestBody @Valid EmailDTO email) throws IOException {
        return resetPasswordUseCase.requestOTP(email);
    }

    @PostMapping("/confirm-password-reset")
    public void confirmPassword(@RequestBody @Valid OTPValidationDTO otpValidationDTO){
        resetPasswordUseCase.checkOTP(otpValidationDTO);
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody @Valid String password) {
        resetPasswordUseCase.changePassword(password);
    }
}
