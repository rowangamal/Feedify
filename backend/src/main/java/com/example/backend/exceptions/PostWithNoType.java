package com.example.backend.exceptions;

public class PostWithNoType extends RuntimeException {
    public PostWithNoType(String message) {
        super(message);
    }
}
