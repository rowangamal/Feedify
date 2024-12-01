package com.example.backend.services;

import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.dtos.UserLoginDTO;
import com.example.backend.login.LoginHandler;
import com.example.backend.login.RoleCheckHandler;
import com.example.backend.login.UserExistsHandler;
import com.example.backend.login.ValidPasswordHandler;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserService userService;

    public AuthUserInfo vertifyUser(UserLoginDTO userLoginDTO){
//        AuthUserInfo authUserInfo = new AuthUserInfo();
        LoginHandler loginHandler = new UserExistsHandler(userService);
        return loginHandler.handle(userLoginDTO);
    }

}
