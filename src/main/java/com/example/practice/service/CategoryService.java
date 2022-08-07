package com.example.practice.service;

import com.example.practice.dto.CategoryDto;
import com.example.practice.entity.Category;
import com.example.practice.exceptions.CategoryNotFoundException;
import com.example.practice.mapper.CategoryMapper;
import com.example.practice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public void createCategory(CategoryDto categoryDto) {
        categoryRepository.save(categoryMapper.fromDto(categoryDto));
    }

    public CategoryDto readCategoryDto(Long id) {
        return categoryMapper.toDto(readCategory(id));
    }

    public Category readCategory(Long id) {
        return categoryRepository.findById((id)).orElseThrow(CategoryNotFoundException::new);
    }

    public void updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(CategoryNotFoundException::new);
        category.setName(categoryDto.getName());
        category.setParentCategory(categoryMapper.fromDto(categoryDto.getParentCategory()));
        category.setCode(categoryDto.getCode());
        category.setDescription(categoryDto.getDescription());
        category.setImage(categoryDto.getImage());
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }
        categoryRepository.deleteById(id);
    }

    public List<CategoryDto> readAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryMapper.toDto(categoryRepository.findAll(pageable).stream().toList());
    }

    public List<Category> getChildCategories(String name) {
        return categoryRepository.getChildCategories(name);
    }
    public List<Long> getChildCategoriesIds(String name) {
        return getChildCategories(name)
                .stream()
                .map(Category::getId)
                .toList();
    }
}
