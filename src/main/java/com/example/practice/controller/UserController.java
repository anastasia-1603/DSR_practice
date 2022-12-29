package com.example.practice.controller;

import com.example.practice.dto.ItemDto;
import com.example.practice.dto.UserDto;
import com.example.practice.service.UserItemService;
import com.example.practice.service.UserService;
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
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserItemService userItemService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserDtoById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Validated(New.class) @RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(defaultValue = "0", name = "page") int page,
                                                     @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok(userService.readAllUsers(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@Validated(Update.class) @RequestBody UserDto user) {
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/items/{itemId}")
    public ResponseEntity<Void> addItemToUser(@PathVariable("userId") Long userId, @PathVariable("itemId") Long itemId) {
        userItemService.addItemToUser(itemId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}/items")
    public ResponseEntity<List<ItemDto>> getUserItems(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userItemService.getUserItems(userId));
    }

//    @GetMapping("/items")
//    public ResponseEntity<List<ItemDto>> getAllItemsWithOwner() {
//        return ResponseEntity.ok(userService.getUserItems(userId));
//    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<Void> deleteUsersItem(@PathVariable("userId") Long userId, @PathVariable("itemId") Long itemId) {
        userItemService.deleteUsersItem(userId, itemId);
        return ResponseEntity.ok().build();
    }
}
