package com.example.backend.controllers;

import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.dtos.UserLoginDTO;
import com.example.backend.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("")
    public ResponseEntity<AuthUserInfo> login(@RequestBody UserLoginDTO userLoginDTO){
        try {
            AuthUserInfo authUserInfo = loginService.vertify(userLoginDTO);
            return ResponseEntity.ok().body(authUserInfo);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
