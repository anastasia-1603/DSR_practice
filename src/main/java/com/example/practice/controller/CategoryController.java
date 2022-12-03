package com.example.practice.controller;

import com.example.practice.dto.CategoryDto;
import com.example.practice.service.CategoryService;
import com.example.practice.service.ItemCategoryService;
import com.example.practice.validator.New;
import com.example.practice.validator.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ItemCategoryService itemCategoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getCategoryDtoById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(@Validated(New.class) @RequestBody CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam(defaultValue = "0", name = "page") int page,
                                                              @RequestParam(defaultValue = "20", name = "size") int size) {
        return ResponseEntity.ok(categoryService.getAllCategories(page, size));
    }

    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        itemCategoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCategory(@Validated(Update.class) @RequestBody CategoryDto categoryDto) {
        categoryService.updateCategory(categoryDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/names-list")
    public ResponseEntity<List<String>> getNamesOfAllCategories() {
        return ResponseEntity.ok(categoryService.getNamesOfAllCategories());
    }
}
