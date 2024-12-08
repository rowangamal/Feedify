package com.example.backend.controllers;
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


@Controller
@RequestMapping("/api/auth")
public class GoogleAuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private SignupService signupService;


    @PostMapping("/signupGoogle")
    public ResponseEntity<Object> signUpWithGoogle(@RequestBody
                                                       UserSignupDTO userSignupDTO) {
        try{
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