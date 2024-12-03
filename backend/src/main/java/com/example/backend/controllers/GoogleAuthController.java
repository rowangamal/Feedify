package com.example.backend.controllers;

// import com.example.backend.GoogleTokenDTO;
import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.dtos.UserLoginDTO;
import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.services.LoginService;
import com.example.backend.services.SignupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// import java.security.NoSuchAlgorithmException;

//@RestController
//@RequestMapping("/api/auth")
@Controller
@RequestMapping("/api/auth")
public class GoogleAuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private SignupService signupService;


   @PostMapping("/signupGoogle")
    public ResponseEntity<Object> signup(@RequestBody UserSignupDTO userSignupDTO) {
        try{
            // System.out.println("helooooo");
            // System.out.println(userSignupDTO.getEmail());
            // System.out.println(userSignupDTO.getUsername());
            // System.out.println(userSignupDTO.getPassword());
            signupService.signupGoogle(userSignupDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            if(e instanceof UserAlreadyExistException)
                return ResponseEntity.unprocessableEntity().body(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/loginGoogle")
    public ResponseEntity<Object> signInWithGoogle(@RequestBody
                                                       UserLoginDTO userLoginDTO){
        try {
            // System.out.println("hello");
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
