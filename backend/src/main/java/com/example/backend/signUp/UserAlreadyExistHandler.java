package com.example.backend.signUp;

import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.services.UserService;

public class UserAlreadyExistHandler extends SignupHandler{
    private final UserService userService;

    public UserAlreadyExistHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleRequest(UserSignupDTO userSignupDTO) {
        User user = userService.getUserByEmail(userSignupDTO.getEmail());
        if(user != null)
            throw new UserAlreadyExistException("User already exists");
    }
}
