package com.app.subscription_manager.exception;

public class UserAlreadyExistsException extends CustomException {
    public UserAlreadyExistsException(String email) {
        super("User with email: " + email + " already exists");
    }
}
