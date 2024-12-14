package com.example.backend.controllers;
import com.example.backend.dtos.AdminDTO;
import com.example.backend.entities.User;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fetch")
public class AdminController {

    @Autowired
    UserService userService;


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/admins")
    public List<AdminDTO> getAllAdmins() {
        return userService.getAllAdmins();
    }

    @PatchMapping("/promote")
    public ResponseEntity<Void> promoteUserToAdmin(@RequestBody Map<String, Long> request) {
        Long userId = request.get("id");
        try {
            userService.promoteToAdmin(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/demote")
    public ResponseEntity<Void> demoteAdminToUser(@RequestBody Map<String, Long> request) {
        Long adminId = request.get("id");
        try {
            userService.demoteToUser(adminId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
