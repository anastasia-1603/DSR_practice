package com.example.practice.exceptions;


public class UserEmailExistsException extends RuntimeException {

    public UserEmailExistsException() {
        super("User with this e-mail already exists");
    }
}
