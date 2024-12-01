package com.example.backend.services;

import com.example.backend.entities.User;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.AdminRepository;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    public User getUserByEmail(String email){
        return userRepository.findUsersByEmail(email).orElseThrow(()-> new UserNotFoundException("Incorrect email or password"));
    }

    public boolean isAdmin(User user){
        return adminRepository.findAdminByUser(user);
    }

}
