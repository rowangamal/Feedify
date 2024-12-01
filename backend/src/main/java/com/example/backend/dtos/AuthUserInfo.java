package com.example.backend.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthUserInfo {
    private String JWTToken;
    private Boolean isAdmin;
}
