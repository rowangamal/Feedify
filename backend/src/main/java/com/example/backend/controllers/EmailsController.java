package com.example.backend.controllers;

import com.example.backend.dto.EmailDTO;
import com.example.backend.services.UserService;
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
    public final UserService userService;

    @PostMapping("/request-password-reset")
    public void resetPassword(@RequestBody @Valid EmailDTO email) throws IOException {
        // TODO Response need to be edited
        resetPasswordUseCase.requestOTP(email);
    }

    @PostMapping("/add")
    public void add() {
        userService.addUserManually();
    }

//    @PostMapping("/confirm-password-reset")
//    public void confirmPassword(@RequestBody @Valid OTPValidationDTO otpValidationDTO){
//        resetPasswordUseCase.checkOTP(otpValidationDTO);
//    }
//
//    @PostMapping("/change-password")
//    public void changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
//        resetPasswordUseCase.changePassword(changePasswordDTO);
//    }
}
