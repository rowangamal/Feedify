package com.example.backend.exceptions;

public class JWTBlacklistedException extends RuntimeException {
    public JWTBlacklistedException(String message) {
        super(message);
    }
}
