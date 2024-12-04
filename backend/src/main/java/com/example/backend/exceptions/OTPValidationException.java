package com.example.backend.exceptions;

public class OTPValidationException extends RuntimeException {
    public OTPValidationException()  {
        super(ErrorTexts.INVALID_OTP.getMessage());
    }
}