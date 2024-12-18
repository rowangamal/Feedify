package com.example.backend.exceptions;

public class InvalidOtpException extends RuntimeException{
    public InvalidOtpException()  {
        super(ErrorTexts.INCORRECT_OTP.getMessage());
    }
}
