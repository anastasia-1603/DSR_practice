package com.example.practice.controller;

import com.example.practice.dto.CategoryDto;
import com.example.practice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.readCategory(id));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryDto>> readAll() {
        return ResponseEntity.ok(categoryService.readAllCategories());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.updateCategory(categoryDto);
        return ResponseEntity.ok().build();
    }
}
