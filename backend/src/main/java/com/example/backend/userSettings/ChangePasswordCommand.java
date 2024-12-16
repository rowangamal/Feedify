package com.example.backend.userSettings;

import com.example.backend.entities.User;
import com.example.backend.exceptions.InvalidCredentialsException;
import com.example.backend.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ChangePasswordCommand extends UserSettingsCommand {
    private final String newPassword;
    private final BCryptPasswordEncoder encoder;
    private String oldPassword;

    public ChangePasswordCommand(User user, UserRepository userRepository, String newPassword,String oldPassword , BCryptPasswordEncoder encoder) {
        super(user, userRepository);
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        this.encoder = encoder;
    }

    @Override
    public String execute() {
        if(user.getPassword() == null || user.getPassword().isEmpty())
            throw new NullPointerException("Password is required, can't be null");
        if(encoder.matches(oldPassword, user.getPassword())){
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
        }
        else
            throw new InvalidCredentialsException("Old password is incorrect");
        return "Password changed successfully";
    }
}
