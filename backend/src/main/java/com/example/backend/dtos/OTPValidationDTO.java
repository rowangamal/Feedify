package com.example.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OTPValidationDTO {

    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 5, message = "OTP must be exactly 5 characters long.")
    private String otp;
}