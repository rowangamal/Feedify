package com.example.backend.controllers;

import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.dtos.UserLoginDTO;
import com.example.backend.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
@RequestMapping("/request/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/")
    public ResponseEntity<AuthUserInfo> login(@RequestBody UserLoginDTO userLoginDTO){
        AuthUserInfo authUserInfo = loginService.vertifyUser(userLoginDTO);
        return ResponseEntity.ok().body(authUserInfo);
    }
}
