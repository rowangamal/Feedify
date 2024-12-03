package com.example.backend.signUp;

import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UsernameTakenException;
import com.example.backend.services.UserService;

public class UsernameTakenHandler extends SignupHandler{
    private final UserService userService;

    public UsernameTakenHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleRequest(UserSignupDTO userSignupDTO) {
        User user = userService.getUserByUsername(userSignupDTO.getUsername());
        if(user != null)
            throw new UsernameTakenException("Username already taken");
        this.setNextHandler(new UserAlreadyExistHandler(userService)).handleRequest(userSignupDTO);
    }
}
