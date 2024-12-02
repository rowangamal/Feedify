package com.example.backend;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GoogleTokenDTO {
    private String email;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String locale;
    private Boolean emailVerified;
    private String sub; // Google user ID

}
