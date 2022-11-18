package com.example.practice.exceptions;

public class DeleteFileException extends RuntimeException{
    public DeleteFileException(String message) {
        super(message);
    }
}
