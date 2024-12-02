package com.example.backend.services;

import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.dtos.UserLoginDTO;
import com.example.backend.login.LoginHandler;
import com.example.backend.login.UserExistsHandler;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    public AuthUserInfo verify(UserLoginDTO userLoginDTO){
        LoginHandler loginHandler = new UserExistsHandler(userService);
        AuthUserInfo authUserInfo = loginHandler.handle(userLoginDTO);
        authUserInfo.setJWTToken(jwtService.generateToken(authUserInfo));
        return authUserInfo;
    }

    public AuthUserInfo verifyGoogleAuth(UserLoginDTO userLoginDTO){
        UserExistsHandler loginHandler = new UserExistsHandler(userService);
        AuthUserInfo authUserInfo = loginHandler.handleGoogleAuth(userLoginDTO);
        authUserInfo.setJWTToken(jwtService.generateToken(authUserInfo));
        return authUserInfo;
    }

}
