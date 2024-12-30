package com.example.backend.controllers;

import com.example.backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LogoutController {

    @Autowired
    private UserService userService;

    @PostMapping("/signout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        try{
            userService.logout(request.getHeader("Authorization"));
            return ResponseEntity.ok().body("Logged out successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
