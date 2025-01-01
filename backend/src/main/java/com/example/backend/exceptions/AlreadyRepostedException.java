package com.example.backend.exceptions;

public class AlreadyRepostedException extends RuntimeException {
    public AlreadyRepostedException(String message) {
        super(message);
    }
}