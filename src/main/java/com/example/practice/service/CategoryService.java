package com.example.practice.service;

import com.example.practice.dto.CategoryDto;
import com.example.practice.dto.CategoryViewDto;
import com.example.practice.entity.Category;
import com.example.practice.exceptions.CategoryExistsException;
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
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new CategoryExistsException("Category with the same name already exists");
        }
        categoryRepository.save(categoryMapper.fromDto(categoryDto));
    }

    public CategoryDto getCategoryDtoById(Long id) {
        return categoryMapper.toDto(getCategoryById(id));
    }

    public Category getCategoryByName(String name) {
        Category c = categoryRepository.findByName(name);
        if (c != null) {
            return c;
        }
        else {
            throw new CategoryNotFoundException();
        }
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById((id)).orElseThrow(CategoryNotFoundException::new);
    }

    public void updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(CategoryNotFoundException::new);
        category.setName(categoryDto.getName());
        category.setParentCategoryId(categoryDto.getParentCategoryId());
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

    public List<CategoryDto> getCategoriesByParentId(Long parentId) {
        return categoryMapper.toDto(categoryRepository.getCategoriesByParentCategoryId(parentId));
    }

    public List<CategoryDto> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryMapper.toDto(categoryRepository.findAll(pageable).stream().toList());
    }

    public List<CategoryDto> getAllCategories() {
        return categoryMapper.toDto(categoryRepository.findAll());
    }

    public List<String> getNamesOfAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .toList();
    }

    public List<Category> getChildCategories(String name) {
        return categoryRepository.getChildCategories(name);
    }

    public List<Category> getChildCategories(Long id) {
        return categoryRepository.getChildCategories(id);
    }

    public List<Long> getChildCategoriesIds(String name) {
        return getChildCategories(name)
                .stream()
                .map(Category::getId)
                .toList();
    }

    public List<CategoryViewDto> getAllCategoriesViewDto() {
        return categoryMapper.toViewDto(categoryRepository.findAll());
    }
}
