package com.example.backend.controllers;

import com.example.backend.GoogleTokenDTO;
import com.example.backend.services.GoogleAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/auth")
public class GoogleAuthController {

    private final GoogleAuthService googleAuthService;

    public GoogleAuthController(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }

    @PostMapping("/signupGoogle")
    public ResponseEntity<String> signUpWithGoogle(@RequestBody GoogleTokenDTO tokenDTO) throws NoSuchAlgorithmException {
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

    @CrossOrigin(origins = "http://localhost:5174")
    @GetMapping("/checkUsernameAvailability")
    public ResponseEntity<?> checkUsernameAvailability(@RequestParam String username) {
        boolean isAvailable = googleAuthService.isUsernameAvailable(username);
        return ResponseEntity.ok().body(new UsernameAvailabilityResponse(isAvailable));
    }

    public static class UsernameAvailabilityResponse {
        private boolean isAvailable;

        public UsernameAvailabilityResponse(boolean isAvailable) {
            this.isAvailable = isAvailable;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailable(boolean available) {
            isAvailable = available;
        }
    }
}
