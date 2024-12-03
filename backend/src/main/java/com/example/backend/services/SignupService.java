package com.example.backend.services;

import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.entities.User;
import com.example.backend.signUp.SignupHandler;
import com.example.backend.signUp.UserAlreadyExistHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SignupService {
    @Autowired
    private UserService userService;

    public void signup(UserSignupDTO userSignupDTO) {
        SignupHandler signupHandler = new UserAlreadyExistHandler(userService);
        signupHandler.handleRequest(userSignupDTO);
        User user = createUserFromDTO(userSignupDTO);
        userService.saveUser(user);
    }

    private User createUserFromDTO(UserSignupDTO userSignupDTO){
        User user = new User();
        user.setFName(userSignupDTO.getFirstName());
        user.setLName(userSignupDTO.getLastName());
        user.setUsername(userSignupDTO.getUsername());
        user.setEmail(userSignupDTO.getEmail());
        user.setPassword(userSignupDTO.getPassword());
        user.setGender(userSignupDTO.isGender());
        user.setBirthDate(userSignupDTO.getDateOfBirth());
        return user;
    }
}
