package com.example.practice.controller;

import com.example.practice.dto.UserDto;
import com.example.practice.dto.UsersItemDto;
import com.example.practice.dto.UsersItemKeeperDto;
import com.example.practice.entity.UsersItem;
import com.example.practice.service.UserItemsService;
import com.example.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/itemsManagement")
public class UsersItemController {

    private final UserItemsService userItemsService;

    @PostMapping
    public ResponseEntity<Void> addItemToUser(@RequestBody UsersItemKeeperDto usersItemKeeperDto) {
        userItemsService.addItemToUser(usersItemKeeperDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<UsersItemDto>> getAll() {

        return ResponseEntity.ok(userItemsService.readAll());
    }
}
