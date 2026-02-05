package com.app.subscription_manager.exception;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(String userId) {
        super("User not found with ID: " + userId);
    }
}
