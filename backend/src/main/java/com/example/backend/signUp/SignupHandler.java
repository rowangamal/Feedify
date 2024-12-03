package com.example.backend.signUp;

import com.example.backend.dtos.UserSignupDTO;

public abstract class SignupHandler {
    protected SignupHandler nextHandler;

    public SignupHandler setNextHandler(SignupHandler nextHandler) {
        this.nextHandler = nextHandler;
        return this.nextHandler;
    }

    public abstract void handleRequest(UserSignupDTO userSignupDTO);
}