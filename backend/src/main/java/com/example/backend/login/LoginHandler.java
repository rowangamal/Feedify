package com.example.backend.login;

import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.dtos.UserLoginDTO;

public abstract class LoginHandler {
    protected LoginHandler next;

    public LoginHandler setNextHandler(LoginHandler handler){
        next = handler;
        return handler;
    }
    public abstract AuthUserInfo handle(UserLoginDTO userLoginDTO);
}
