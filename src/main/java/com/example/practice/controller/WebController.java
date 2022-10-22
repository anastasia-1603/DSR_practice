package com.example.practice.controller;

import com.example.practice.entity.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("item", new Item(1L, "new item", "//", "/img/cat1", null, 1L, null));
        return "index";
    }
}
