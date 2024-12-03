package com.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String gender;
    private String dateOfBirth;
    private String email;
    private String password;
}
