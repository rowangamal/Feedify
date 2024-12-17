package com.example.backend.exceptions;

public class DuplicatedReportException extends RuntimeException {
    public DuplicatedReportException(String message) {
        super(message);
    }
}
