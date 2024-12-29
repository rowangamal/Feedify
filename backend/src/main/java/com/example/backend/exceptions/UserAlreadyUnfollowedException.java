package com.example.backend.exceptions;

public class UserAlreadyUnfollowedException extends RuntimeException {
    public UserAlreadyUnfollowedException()  {
        super(ErrorTexts.USER_ALREADY_UNFOLLOWED.getMessage());
    }
}