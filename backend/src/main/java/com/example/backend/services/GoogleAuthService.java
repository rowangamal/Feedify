package com.example.backend.services;

import com.example.backend.GoogleTokenDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleAuthService {

    private final Map<String, GoogleTokenDTO> registeredUsers = new HashMap<>();

    public boolean createUser(GoogleTokenDTO tokenDTO) {
        if (registeredUsers.containsKey(tokenDTO.getEmail())) {
            return false;
        }
        registeredUsers.put(tokenDTO.getEmail(), tokenDTO);
        return true;
    }

    public boolean checkUserExists(String email) {
        return registeredUsers.containsKey(email);
    }
}
