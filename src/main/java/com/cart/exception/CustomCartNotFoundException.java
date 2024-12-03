package com.cart.exception;

public class CustomCartNotFoundException extends RuntimeException {
    public CustomCartNotFoundException(String message) {
        super(message);
    }
}