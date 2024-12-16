package com.example.backend.userSettings;

import com.example.backend.entities.User;
import com.example.backend.exceptions.UsernameTakenException;
import com.example.backend.repositories.UserRepository;

public class ChangeUsernameCommand extends UserSettingsCommand {
    private String newUsername;


    public ChangeUsernameCommand(User user, UserRepository userRepository, String newUsername) {
        super(user, userRepository);
        this.newUsername = newUsername;
    }

    @Override
    public String execute() {
        userRepository.findUsersByUsername(newUsername).ifPresent(user -> {
            throw new UsernameTakenException("Username already exists");
        });
        user.setUsername(newUsername);
        userRepository.save(user);
        return newUsername;
    }
}
