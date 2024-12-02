package com.example.backend.login;

import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.dtos.UserLoginDTO;
import com.example.backend.entities.User;
import com.example.backend.services.UserService;

public class RoleCheckHandler extends LoginHandler{
    private final User user;
    private final UserService userService;

    public RoleCheckHandler(User user, UserService userService) {
        this.user = user;
        this.userService = userService;
    }

    @Override
    public AuthUserInfo handle(UserLoginDTO userLoginDTO) {
        boolean isAdmin = userService.isAdmin(this.user);
        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setIsAdmin(isAdmin);
        authUserInfo.setUserId(this.user.getId());
        return authUserInfo;
    }

}
