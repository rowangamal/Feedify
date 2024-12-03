package com.example.backend.controllers;

import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.services.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    private SignupService signupService;

    @PostMapping("")
    public ResponseEntity<Object> signup(@RequestBody UserSignupDTO userSignupDTO) {
        return null;
    }
}