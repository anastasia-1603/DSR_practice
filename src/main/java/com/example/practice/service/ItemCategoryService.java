package com.example.practice.service;

import com.example.practice.dto.CategoryDto;
import com.example.practice.dto.ItemDto;
import com.example.practice.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemCategoryService {

    private final CategoryService categoryService;
    private final ItemService itemService;

    public void deleteCategory(Long id) {
        Category category = categoryService.getCategoryById(id);
        List<ItemDto> items = itemService.getAllItemsByCategory(category);
        List<Category> child = categoryService.getChildCategories(category.getName());
        if ((items == null || items.isEmpty()) && (child.size() <= 1)) {
            categoryService.deleteCategory(id);
        }
    }

    public List<ItemDto> getAllItemsByCategory(CategoryDto categoryDto) {
        Category category = categoryService.getCategoryById(categoryDto.getId());
        return itemService.getAllItemsByCategory(category);
    }

    public List<ItemDto> getAllItemsByCategoryId(Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return itemService.getAllItemsByCategory(category);
    }

    public List<ItemDto> getAllItemsByCategoryName(String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        return itemService.getAllItemsByCategory(category);
    }
}
