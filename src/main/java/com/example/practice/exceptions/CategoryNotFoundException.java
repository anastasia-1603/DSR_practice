package com.example.practice.exceptions;

import com.example.practice.entity.Category;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {
        super("Category not found");
    }
}
