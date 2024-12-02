package com.example.backend.services;

import com.example.backend.GoogleTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleAuthService {

    private final Map<String, GoogleTokenDTO> registeredUsers = new HashMap<>();
    private final Map<String, String> usernames = new HashMap<>();

    public boolean createUser(GoogleTokenDTO tokenDTO) throws NoSuchAlgorithmException {
        if (registeredUsers.containsKey(tokenDTO.getEmail())) {
            return false;
        }
        String username = generateUsername(tokenDTO.getEmail());
        registeredUsers.put(tokenDTO.getEmail(), tokenDTO);
        usernames.put(tokenDTO.getEmail(), username);

        return true;
    }

    public boolean checkUserExists(String email) {
        return registeredUsers.containsKey(email);
    }

    public boolean isUsernameAvailable(String username) {
        return !usernames.containsValue(username);
    }

    private static String generateUsername(String email) throws NoSuchAlgorithmException {
        String localPart = email.split("@")[0];
        String emailHash = hashEmail(email);
        return "%s_%s".formatted(localPart, emailHash);
    }

    private static String hashEmail(String email) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(email.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
