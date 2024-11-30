package com.example.backend.exceptions;

public class PostOutOfLimitException extends RuntimeException {
    public PostOutOfLimitException(String message) {
        super(message);
    }
}
