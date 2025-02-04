package com.example.backend.autoLoad;

import com.example.backend.entities.User;
import com.example.backend.entities.Admin;
import com.example.backend.repositories.AdminRepository;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class AdminLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;


    @Override
    public void run(String... args) throws Exception {
        if(adminRepository.count() == 0){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            User user = new User();
            user.setUsername("Feedify_admin");
            user.setPassword(encoder.encode("rowan@123@armia"));
            user.setEmail("swefeedify@gmail.com");
            user.setFName("Feedify");
            user.setLName("Admin");
            user.setGender(true);
            user.setBirthDate(Date.valueOf("2025-01-01"));

            userRepository.save(user);
            user = userRepository.findUsersByUsername("Feedify_admin").get();
            Admin admin = new Admin();
            admin.setUser(user);
            adminRepository.save(admin);
        }
    }
}
