package com.example.backend.userSettings;

import com.example.backend.entities.Post;
import com.example.backend.entities.User;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.UserRepository;

public abstract class UserSettingsCommand {
    protected User user;
    protected final UserRepository userRepository;
    public UserSettingsCommand(User user, UserRepository userRepository) {
        this.user = user;
        this.userRepository = userRepository;
    }

    public abstract String execute() ;
}
