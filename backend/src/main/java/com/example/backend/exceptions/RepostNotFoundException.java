package com.example.backend.exceptions;

public class RepostNotFoundException extends RuntimeException{
    public RepostNotFoundException(String message) {
        super(message);
    }
}
