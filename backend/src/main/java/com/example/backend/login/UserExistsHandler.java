package com.example.backend.login;

import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.dtos.UserLoginDTO;
import com.example.backend.entities.User;
import com.example.backend.services.UserService;

public class UserExistsHandler extends LoginHandler{
    private final UserService userService;

    public UserExistsHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AuthUserInfo handle(UserLoginDTO userLoginDTO) {
        User user = userService.getUserByEmail(userLoginDTO.getEmail());
        return this.setNextHandler(new ValidPasswordHandler(user, userService)).handle(userLoginDTO);
    }


    public AuthUserInfo handleGoogleAuth(UserLoginDTO userLoginDTO){
        User user = userService.getUserByEmail(userLoginDTO.getEmail());
        return setNextHandler(new RoleCheckHandler(user, userService)).handle(userLoginDTO);
    }



}
