package com.example.practice.exceptions;

public class UsersItemNotFoundException extends RuntimeException {

    public UsersItemNotFoundException() {
        super("User's item not found");
    }
}
