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
        System.out.println("Checking if email exists: " + userSignupDTO.getEmail());
        try {
            userService.getUserByEmail(userSignupDTO.getEmail());
            throw new UserAlreadyExistException("Email already in use");
        } catch (UserNotFoundException e) {
            System.out.println("Email not found: " + userSignupDTO.getEmail());
            if (nextHandler != null) {
                nextHandler.handleRequest(userSignupDTO);
            }
        }
    }

}