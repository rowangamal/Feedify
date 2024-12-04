package com.example.backend.signUp;

import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.exceptions.InvalidEmailException;

public class InvalidEmailHandler extends SignupHandler {

    @Override
    public void handleRequest(UserSignupDTO userSignupDTO) {
        if (userSignupDTO.getEmail() == null || !userSignupDTO.getEmail().contains("@gmail.com")) {
            throw new InvalidEmailException("Invalid email");
        }
        super.handleRequest(userSignupDTO);
    }
}
