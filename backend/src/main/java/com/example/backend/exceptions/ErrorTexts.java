package com.example.backend.exceptions;

public class ErrorTexts {
    public static final ErrorMessage INVALID_OTP
            = new ErrorMessage("Email or code is incorrect.");
    public static final ErrorMessage SERVICE_NOT_AVAILABLE
            = new ErrorMessage("Service is not available right now, please try again later.");
    public static final ErrorMessage OTP_EXPIRED
            = new ErrorMessage("The OTP you have entered has Expired, please try again later.");
    public static final ErrorMessage INCORRECT_OTP
            = new ErrorMessage("The OTP you have entered is not correct for the provided email, please try another one.");
    public static final ErrorMessage SIGNED_UP_WITH_GMAIL =
            new ErrorMessage("This Email is signed up with Gmail and does not need to reset password, try to login with this Gmail using login with google.");
}
