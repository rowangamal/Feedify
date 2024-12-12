package com.example.backend.dtos;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ResetPasswordDTO {
    @Email
    private String email;

    private String newPassword;
}
