package com.example.backend.login;

import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.dtos.UserLoginDTO;
import com.example.backend.entities.User;
import com.example.backend.exceptions.InvalidCredentialsException;
import com.example.backend.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ValidPasswordHandler extends LoginHandler{
    private final User user;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    public ValidPasswordHandler(User user,  UserService userService) {
        this.user = user;
        this.userService = userService;
        this.encoder = new BCryptPasswordEncoder(12);
    }

    @Override
    public AuthUserInfo handle(UserLoginDTO userLoginDTO) {
        String hashedPassword = encoder.encode(userLoginDTO.getPassword());
        if (this.user.getPassword().equals(hashedPassword)){
            return setNextHandler(new RoleCheckHandler(user, userService)).handle(userLoginDTO);
        } else
            throw new InvalidCredentialsException("Incorrect email or password");
    }

}
