package com.example.practice.controller;

import com.example.practice.dto.CategoryDto;
import com.example.practice.service.CategoryService;
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

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getCategoryDtoById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(@Validated(New.class) @RequestBody CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam(defaultValue = "0", name = "page") int page,
                                                              @RequestParam(defaultValue = "20", name = "size") int size) {
        return ResponseEntity.ok(categoryService.readAllCategories(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCategory(@Validated(Update.class) @RequestBody CategoryDto categoryDto) {
        categoryService.updateCategory(categoryDto);
        return ResponseEntity.ok().build();
    }
}
