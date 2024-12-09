package com.example.backend.exceptions;

public class PostWithZeroContent extends RuntimeException {
    public PostWithZeroContent(String message) {
        super(message);
    }
}
