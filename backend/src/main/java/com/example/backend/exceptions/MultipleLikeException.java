package com.example.backend.exceptions;

public class MultipleLikeException extends RuntimeException {
    public MultipleLikeException(String message) {
        super(message);
    }
}
