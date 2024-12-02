package com.example.backend.controllers;

import com.example.backend.GoogleTokenDTO;
import com.example.backend.services.GoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class GoogleAuthController {
    @Autowired
    private final GoogleAuthService googleAuthService;

    public GoogleAuthController(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }

    @PostMapping("/signupGoogle")
    public ResponseEntity<String> signUpWithGoogle(@RequestBody GoogleTokenDTO tokenDTO) {
        boolean userCreated = googleAuthService.createUser(tokenDTO);
        if (userCreated) {
            return ResponseEntity.ok("User registered successfully!");
        }
        return ResponseEntity.status(409).body("User already exists");
    }

    @PostMapping("/loginGoogle")
    public ResponseEntity<String> signInWithGoogle(@RequestBody GoogleTokenDTO tokenDTO) {
        boolean userExists = googleAuthService.checkUserExists(tokenDTO.getEmail());
        if (userExists) {
            return ResponseEntity.ok("User signed in successfully!");
        }
        return ResponseEntity.status(404).body("User not registered");
    }
}
