package com.example.practice.controller;

import com.example.practice.dto.CategoryDto;
import com.example.practice.dto.CategoryViewDto;
import com.example.practice.dto.ItemDto;
import com.example.practice.dto.NewItemDto;
import com.example.practice.entity.Category;
import com.example.practice.service.CategoryService;
import com.example.practice.service.ItemCategoryService;
import com.example.practice.service.ItemService;
import com.example.practice.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeWebController {

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
                             @RequestParam Long code, Model model) {
        NewItemDto item = new NewItemDto();
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        item.setName(name);
        item.setDescription(description);
        item.setCategoryName(categoryName);
        item.setCode(code);
        item.setImage(filename);
        itemService.createItem(item);
        String uploadDir = "static/img/";
        FileUploadUtil.saveFile(uploadDir, filename, multipartFile);
        List<ItemDto> items = itemService.getAllItems();
        List<CategoryViewDto> categories = categoryService.getAllCategoriesViewDto();
        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        return "index";
    }
}
