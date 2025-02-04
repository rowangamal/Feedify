package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    public String username;
    public String profilePic;
    public long id;
    public UserInfoDTO(String username, String profilePic) {
        this.username = username;
        this.profilePic = profilePic;
    }
}
