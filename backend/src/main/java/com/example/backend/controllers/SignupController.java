package com.example.backend.controllers;

import com.example.backend.dtos.EmailDTO;
import com.example.backend.dtos.OTPValidationDTO;
import com.example.backend.dtos.UserSignupDTO;
import com.example.backend.exceptions.*;
import com.example.backend.services.OTPService;
import com.example.backend.services.SignupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    private SignupService signupService;

    @Autowired
    private OTPService otpService;

    @PostMapping("")
    public ResponseEntity<Object> signup(@RequestBody UserSignupDTO userSignupDTO) {
        try{
            signupService.signup(userSignupDTO);
            return ResponseEntity.created(URI.create("/signup")).build();
        } catch (Exception e){
            if(e instanceof UserAlreadyExistException || e instanceof UsernameTakenException)
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            else if(e instanceof InvalidEmailException)
                return ResponseEntity.badRequest().body(e.getMessage());

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify-email-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody @Valid OTPValidationDTO otpValidationDTO){
        try {
            otpService.checkSignUpOTP(otpValidationDTO);
            return ResponseEntity.ok("OTP is correct.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email is not registered in the system.");
        } catch (InvalidOtpException | OtpExpiredException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/request-verification-otp")
    public ResponseEntity<String> requestVerificationOTP(@RequestBody @Valid EmailDTO email) {
        try {
            signupService.saveVerificationOTP(email.getEmail());
            return ResponseEntity.ok("OTP sent successfully to your email. Please check your inbox.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email is not registered in the system.");
        } catch (ServiceUnavailableException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (EmailSignedUpWithGoogleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}