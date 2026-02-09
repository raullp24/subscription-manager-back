package com.app.subscription_manager.exception;

public class SubscriptionNotFoundException extends CustomException {
    public SubscriptionNotFoundException(String id) {
        super("Subscription with id " + id + " not found");
    }

}
