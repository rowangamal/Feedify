package com.example.backend.exceptions;

public class ErrorMessage {
    private final String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    // Method to get the message with optional arguments for formatting
    public String getMessage(Object... args) {
        return String.format(this.message, args);
    }

    @Override
    public String toString() {
        return message;
    }
}

