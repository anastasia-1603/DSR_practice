package com.example.practice.service;

import com.example.practice.entity.Category;
import com.example.practice.exceptions.CategoryExistsException;
import com.example.practice.exceptions.CategoryNotFoundException;
import com.example.practice.exceptions.UserExistsException;
import com.example.practice.exceptions.UserNotFoundException;
import com.example.practice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void create(Category category) {
        if (categoryRepository.existsById(category.getId())) {
            throw new CategoryExistsException("Category with id = " + category.getId() + "already exists");
        }
        categoryRepository.save(category);
    }

    public Optional<Category> read(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }
        return categoryRepository.findById((id));
    }

    public void update(Category category) {
        if (!categoryRepository.existsById(category.getId())) {
            throw new CategoryNotFoundException();
        }
        categoryRepository.save(category);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }
        categoryRepository.deleteById(id);
    }

    public List<Category> readAll() {
        return categoryRepository.findAll();
    }



}
