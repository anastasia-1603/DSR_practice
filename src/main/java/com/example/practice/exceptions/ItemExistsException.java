package com.example.practice.exceptions;


public class ItemExistsException extends RuntimeException {
    public ItemExistsException(String message) {
        super(message);
    }
}
