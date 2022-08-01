package com.example.practice.service;

import com.example.practice.dto.CategoryDto;
import com.example.practice.exceptions.CategoryExistsException;
import com.example.practice.exceptions.CategoryNotFoundException;
import com.example.practice.mapper.CategoryMapper;
import com.example.practice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public void createCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsById(categoryDto.getId())) {
            throw new CategoryExistsException("Category with id = " + categoryDto.getId() + "already exists");
        }
        categoryRepository.save(categoryMapper.fromDto(categoryDto));
    }

    public CategoryDto readCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }
        return categoryMapper.toDto(categoryRepository.findById((id)).get());
    }

    public void updateCategory(CategoryDto categoryDto) {
        if (!categoryRepository.existsById(categoryDto.getId())) {
            throw new CategoryNotFoundException();
        }
        categoryRepository.save(categoryMapper.fromDto(categoryDto));
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }
        categoryRepository.deleteById(id);
    }

    public List<CategoryDto> readAllCategories() {
        return categoryMapper.toDto(categoryRepository.findAll());
    }
}
