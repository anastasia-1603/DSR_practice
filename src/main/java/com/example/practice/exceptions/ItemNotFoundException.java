package com.example.practice.exceptions;


public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException() {
        super("Item not found");
    }
}
