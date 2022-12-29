package com.example.practice.controller;

import com.example.practice.dto.ItemDto;
import com.example.practice.dto.UserDto;
import com.example.practice.service.UserItemService;
import com.example.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebUserController {

    private final UserService userService;
    private final UserItemService userItemService;

    @GetMapping("/web/users")
    public String getAllUsersPaginated(Model model,
                                       @RequestParam(defaultValue = "0", name = "page") int page,
                                       @RequestParam(defaultValue = "10", name = "size") int size) {
        Page<UserDto> users = userService.getPageUsers(page, size);
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/web/users/create")
    public String createUserPage(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "create-user";
    }

//    @GetMapping("/web/users/test")
//    public void testPage() {
//        Page<UserDto> users = userService.getPageUsers(0, 10);
//        users.getContent();
//        users.getContent().get(0);
//    }

    @PostMapping("/web/users/create")
    public String createUserSubmit(@RequestParam String surname,
                                   @RequestParam String name,
                                   @RequestParam String patronymic,
                                   @RequestParam String email) {
        userService.createUser(surname, name, patronymic, email);
        return "redirect:/web/users"; 
    }

    @GetMapping("/web/users/update/{userId}")
    public String updateUserPage(Model model,
                                 @PathVariable Long userId,
                                 @RequestParam(defaultValue = "0", name = "page") int page,
                                 @RequestParam(defaultValue = "10", name = "size") int size) {
        UserDto user = userService.getUserDtoById(userId);
        model.addAttribute("user", user);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "update-user";
    }

    @GetMapping("/web/users/{userId}")
    public String getUser(Model model,
                         @PathVariable Long userId) {
        UserDto user = userService.getUserDtoById(userId);
        List<ItemDto> items = userItemService.getUserItems(userId);
        model.addAttribute("user", user);
        model.addAttribute("items", items);
        return "user-card";
    }

    @PostMapping("/web/users/update/{userId}")
    public String updateUserSubmit(@PathVariable Long userId,
                                   @RequestParam String surname,
                                   @RequestParam String name,
                                   @RequestParam String patronymic,
                                   @RequestParam String email,
                                   @RequestParam(defaultValue = "0", name = "page") int page,
                                   @RequestParam(defaultValue = "10", name = "size") int size) {
        userService.updateUser(userId, surname, name, patronymic, email);
        return "redirect:/web/users?size=" + size + "&page=" + page;
    }

    @GetMapping("/web/users/delete/{userId}")
    public String deleteUser(@PathVariable Long userId,
                             @RequestParam(defaultValue = "0", name = "page") int page,
                             @RequestParam(defaultValue = "10", name = "size") int size) {
        userService.deleteUser(userId);
        return "redirect:/web/users?size=" + size + "&page=" + page;
    }
}
