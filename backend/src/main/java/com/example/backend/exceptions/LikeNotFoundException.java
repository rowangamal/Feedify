package com.example.backend.exceptions;

public class LikeNotFoundException extends RuntimeException {
    public LikeNotFoundException(String message) {
        super(message);
    }
}
