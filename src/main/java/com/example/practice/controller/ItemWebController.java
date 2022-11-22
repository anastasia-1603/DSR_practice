package com.example.practice.controller;

import com.example.practice.dto.CategoryViewDto;
import com.example.practice.dto.ItemDto;
import com.example.practice.dto.NewItemDto;
import com.example.practice.service.CategoryService;
import com.example.practice.service.ItemCategoryService;
import com.example.practice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemWebController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final ItemCategoryService itemCategoryService;

    @GetMapping({"/web/items", "/web"})
    public String home(Model model) {
        List<ItemDto> items = itemService.getAllItems();
        List<CategoryViewDto> categories = categoryService.getAllCategoriesViewDto();
        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/web/items/category/{categoryId}")
    public String getItemsByCategory(@PathVariable Long categoryId, Model model) {
        List<ItemDto> items = itemCategoryService.getAllItemsByCategoryId(categoryId);
        List<CategoryViewDto> categories = categoryService.getAllCategoriesViewDto();
        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/web/items/create")
    public String createItemPage(Model model) {
        NewItemDto item = new NewItemDto();
        List<String> categories = categoryService.getNamesOfAllCategories();
        model.addAttribute("item", item);
        model.addAttribute("categories", categories);
        return "create-item";
    }

    @PostMapping("/web/items/create")
    public String createItemSubmit(@RequestParam String name, @RequestParam String description,
                             @RequestParam String categoryName, @RequestParam("image") MultipartFile multipartFile,
                             @RequestParam Long code) {
        itemService.createItem(name, description, categoryName, multipartFile, code);
        return "redirect:/web";
    }

    @GetMapping("/web/items/update/{itemId}") // todo разобраться как сделать ссылку
    public String updateItemPage(Model model, @PathVariable Long itemId) {
        NewItemDto item = itemService.getNewItemDtoById(itemId);
        List<String> categories = categoryService.getNamesOfAllCategories();
        model.addAttribute("item", item);
        model.addAttribute("categories", categories);
        return "update-item";
    }

    @PostMapping("/web/items/update/{itemId}")
    public String updateItemSubmit(@PathVariable Long itemId, @RequestParam String name, @RequestParam String description,
                             @RequestParam String categoryName, @RequestParam("image") MultipartFile multipartFile,
                             @RequestParam Long code) {
        itemService.updateItem(itemId, name, description, categoryName, multipartFile, code);
        return "redirect:/web";
    }

    @GetMapping("/web/items/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return "redirect:/web/items/";
    }

}
