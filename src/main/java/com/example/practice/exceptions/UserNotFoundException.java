package com.example.practice.exceptions;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User is not found");
    }
}
