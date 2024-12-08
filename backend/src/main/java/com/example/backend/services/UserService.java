package com.example.backend.services;

import com.example.backend.entities.User;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public User getUserByEmail(String email){
        return userRepository.findUsersByEmail(email).orElseThrow(()-> new UserNotFoundException("Incorrect email or password"));
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword); // TODO need to use password encoder
        userRepository.save(user);
    }
}