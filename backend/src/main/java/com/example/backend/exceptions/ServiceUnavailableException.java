package com.example.backend.exceptions;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException() {
        super(ErrorTexts.SERVICE_NOT_AVAILABLE.getMessage());
    }
}