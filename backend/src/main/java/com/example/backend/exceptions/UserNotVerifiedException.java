package com.example.backend.exceptions;

public class UserNotVerifiedException extends RuntimeException {
    public UserNotVerifiedException()  {
        super(ErrorTexts.USER_NOT_VERIFIED.getMessage());
    }
}