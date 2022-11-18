package com.example.practice.controller;

import com.example.practice.dto.UserDto;
import com.example.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserWebController {

    private final UserService userService;

    @GetMapping("/web/users")
    public String users(Model model) {
        List<UserDto> users = userService.readAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
    //todo добавление юзера
}
