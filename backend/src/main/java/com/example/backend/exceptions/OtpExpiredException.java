package com.example.backend.exceptions;

public class OtpExpiredException extends RuntimeException{
    public OtpExpiredException()  {
        super(ErrorTexts.OTP_EXPIRED.getMessage());
    }
}
