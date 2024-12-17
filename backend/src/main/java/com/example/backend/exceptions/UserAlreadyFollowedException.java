package com.example.backend.exceptions;

public class UserAlreadyFollowedException extends RuntimeException {
    public UserAlreadyFollowedException()  {
        super(ErrorTexts.USER_ALREADY_FOLLOWED.getMessage());
    }
}