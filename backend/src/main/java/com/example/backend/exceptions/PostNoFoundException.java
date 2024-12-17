package com.example.backend.exceptions;

public class PostNoFoundException extends RuntimeException{
    public PostNoFoundException(String message) {
        super(message);
    }
}
