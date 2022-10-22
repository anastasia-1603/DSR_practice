package com.example.practice.controller;

import com.example.practice.dto.ItemDto;
import com.example.practice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final ItemService itemService;

    @GetMapping("")
    public String index(Model model) {
        List<ItemDto> items = itemService.getAllItems(0, 10);
        model.addAttribute("items", items);
        return "index";
    }
}
