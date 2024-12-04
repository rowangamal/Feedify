package com.example.backend.controllers;

import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.exceptions.UsernameTakenException;
import com.example.backend.services.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    private SignupService signupService;

    @PostMapping("")
    public ResponseEntity<Object> signup(@RequestBody UserSignupDTO userSignupDTO) {
        try{
            signupService.signup(userSignupDTO);
            return ResponseEntity.created(URI.create("/signup")).build();
        } catch (Exception e){
            if(e instanceof UserAlreadyExistException || e instanceof UsernameTakenException)
                return ResponseEntity.unprocessableEntity().body(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
