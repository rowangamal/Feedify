package com.example.backend.controllers;

import com.example.backend.GoogleTokenDTO;
import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.dtos.UserLoginDTO;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.services.GoogleAuthService;
import com.example.backend.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

//@RestController
//@RequestMapping("/api/auth")
@Controller
@RequestMapping("/api/auth")
public class GoogleAuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private GoogleAuthService googleAuthService;


//    @PostMapping("/signupGoogle")
//    public ResponseEntity<String> signUpWithGoogle(@RequestBody GoogleTokenDTO tokenDTO) throws NoSuchAlgorithmException {
//        boolean userCreated = googleAuthService.createUser(tokenDTO);
//        if (userCreated) {
//            return ResponseEntity.ok("User registered successfully!");
//        }
//        return ResponseEntity.status(409).body("User already exists");
//    }

    @PostMapping("/loginGoogle")
    public ResponseEntity<Object> signInWithGoogle(@RequestBody
                                                       UserLoginDTO userLoginDTO){
        try {
            System.out.println("hello");
            AuthUserInfo authUserInfo = loginService.verifyGoogleAuth(userLoginDTO);
            return ResponseEntity.ok().body(authUserInfo);
        } catch (Exception e){
            if (e instanceof UserNotFoundException){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
