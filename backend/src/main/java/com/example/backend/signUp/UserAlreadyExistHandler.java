package com.example.backend.signUp;

import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.services.UserService;

public class UserAlreadyExistHandler extends SignupHandler {
    private final UserService userService;

    public UserAlreadyExistHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleRequest(UserSignupDTO userSignupDTO) {
        if (userSignupDTO.getEmail() == null || userSignupDTO.getEmail().isEmpty()) {
            throw new UserAlreadyExistException("Email is empty or invalid");
        }

        try {
            userService.getUserByEmail(userSignupDTO.getEmail());
            throw new UserAlreadyExistException("User already exists");
        } catch (UserNotFoundException e) {
            super.handleRequest(userSignupDTO);
        }
    }

}
