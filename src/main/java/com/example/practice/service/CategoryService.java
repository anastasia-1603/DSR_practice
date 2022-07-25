package com.example.practice.service;

import com.example.practice.entity.Category;
import com.example.practice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Optional<Category> read(Long id) {
        return categoryRepository.findById((id));
    }

    public void create(Category category) {
        categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> readAll() {
        return categoryRepository.findAll();
    }

    public boolean update(Category category) {
        if (categoryRepository.existsById(category.getId())) {
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

}
