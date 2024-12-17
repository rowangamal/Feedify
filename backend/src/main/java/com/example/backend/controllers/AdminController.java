package com.example.backend.controllers;
import com.example.backend.dtos.AdminDTO;
import com.example.backend.dtos.DemoteAdminRequestDTO;
import com.example.backend.dtos.PromoteUserRequestDTO;
import com.example.backend.dtos.UserDTO;
import com.example.backend.entities.User;
import com.example.backend.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/admins")
    public List<AdminDTO> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @PostMapping("/promote")
    public ResponseEntity<Void> promoteUserToAdmin(@RequestBody PromoteUserRequestDTO request) {
        try {
            Long userId = request.getUserId();
            adminService.promoteToAdmin(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/demote")
    public ResponseEntity<Void> demoteAdminToUser(@RequestBody DemoteAdminRequestDTO request) {
        try {
            Long adminId = request.getAdminId();
            adminService.demoteToUser(adminId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
