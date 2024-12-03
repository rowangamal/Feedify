package com.example.backend.services;

import com.example.backend.entities.User;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByEmail(String email){
        return userRepository.findUsersByEmail(email).
                orElseThrow(()-> new UserNotFoundException("Incorrect email or password"));
    }

    public User getUserByUsername(String username){
        return userRepository.findUsersByUsername(username).
                orElseThrow(()-> new UserNotFoundException("Incorrect email or password"));
    }

    public User getUserById(long id){
        return userRepository.findUserById(id).
                orElseThrow(()-> new UserNotFoundException("User not found"));
    }

}