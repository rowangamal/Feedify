package com.example.backend.signUp;

import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.exceptions.UsernameTakenException;
import com.example.backend.services.UserService;

public class UsernameTakenHandler extends SignupHandler {
    private final UserService userService;

    public UsernameTakenHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleRequest(UserSignupDTO userSignupDTO) {
        try {
            userService.getUserByUsername(userSignupDTO.getUsername());
            throw new UsernameTakenException("Username already taken");
        }
        catch (UserNotFoundException e) {
            super.handleRequest(userSignupDTO);
        }
    }
}