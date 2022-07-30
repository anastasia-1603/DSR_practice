package com.example.practice.exceptions;


public class CategoryExistsException extends RuntimeException {

    public CategoryExistsException(String message) {
        super(message);
    }
}
