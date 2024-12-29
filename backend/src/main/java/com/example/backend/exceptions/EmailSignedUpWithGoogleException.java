package com.example.backend.exceptions;

public class EmailSignedUpWithGoogleException extends RuntimeException{
    public EmailSignedUpWithGoogleException()  {
        super(ErrorTexts.SIGNED_UP_WITH_GMAIL.getMessage());
    }
}
