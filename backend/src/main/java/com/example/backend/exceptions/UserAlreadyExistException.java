package com.example.backend.exceptions;

public class UserAlreadyExistException extends RuntimeException {
  public UserAlreadyExistException(String message) {
    super(message);
  }
}
