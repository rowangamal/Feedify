package com.example.backend.exceptions;

public class ErrorTexts {
    public static final ErrorMessage INVALID_OTP = new ErrorMessage("Email or code is incorrect.");
    public static final ErrorMessage USER_ALREADY_FOLLOWED = new ErrorMessage("You are already following this user.");
    public static final ErrorMessage USER_ALREADY_UNFOLLOWED = new ErrorMessage("User is already unfollowed.");
}
