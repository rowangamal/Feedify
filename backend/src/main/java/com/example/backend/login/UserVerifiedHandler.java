package com.example.backend.login;

import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.dtos.UserLoginDTO;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserNotVerifiedException;
import com.example.backend.services.UserService;

public class UserVerifiedHandler extends LoginHandler{
    private final UserService userService;
    private final User user;

    public UserVerifiedHandler(User user,UserService userService) {
        this.user = user;
        this.userService = userService;
    }

    @Override
    public AuthUserInfo handle(UserLoginDTO userLoginDTO) {
        if(!user.getIsVerified()){
            throw new UserNotVerifiedException();
        }
        return this.setNextHandler(new RoleCheckHandler(user, userService)).handle(userLoginDTO);
    }
}

