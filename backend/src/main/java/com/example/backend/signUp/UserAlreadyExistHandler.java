package com.example.backend.signUp;

import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.services.UserService;

public class UserAlreadyExistHandler extends SignupHandler{
    private final UserService userService;

    public UserAlreadyExistHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleRequest(UserSignupDTO userSignupDTO) {
        try{
            userService.getUserByEmail(userSignupDTO.getEmail());
        }
        catch(UserNotFoundException e){
            this.setNextHandler(new InvalidEmailHandler()).handleRequest(userSignupDTO);
            return;
        }
        throw new UserAlreadyExistException("User already exists");
    }
}
