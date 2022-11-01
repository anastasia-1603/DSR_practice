package com.example.practice.controller;

import com.example.practice.dto.CategoryDto;
import com.example.practice.dto.ItemDto;
import com.example.practice.entity.Category;
import com.example.practice.service.CategoryService;
import com.example.practice.service.ItemCategoryService;
import com.example.practice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web")
public class HomeWebController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final ItemCategoryService itemCategoryService;

    @GetMapping({"/items", "/"})
    public String home(Model model) {
        List<ItemDto> items = itemService.getAllItems();
        List<String> categories = categoryService.getNamesOfAllCategories();
        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/items/category/{categoryId}")
    public String getItemsByCategory(@PathVariable Long categoryId, Model model) {
        List<ItemDto> items = itemCategoryService.getAllItemsByCategoryId(categoryId);
        List<String> categories = categoryService.getNamesOfAllCategories();
        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        return "index"; //todo
    }
}
