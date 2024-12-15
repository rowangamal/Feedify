package com.example.backend.services;

import com.example.backend.dtos.UserInfoDTO;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsInfo {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public UserInfoDTO getUserSettings() {
        long userId = userService.getUserId();
        return userRepository.findById(userId).map(user -> new UserInfoDTO(user.getUsername(), user.getPictureURL())).orElse(null);

    }
}
