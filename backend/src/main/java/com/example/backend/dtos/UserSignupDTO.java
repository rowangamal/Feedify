package com.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserSignupDTO {
    private String firstName;
    private String lastName;
    private String username;
    private boolean gender;
    private Date dateOfBirth;
    private String email;
    private String password;
}
