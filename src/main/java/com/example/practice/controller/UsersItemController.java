package com.example.practice.controller;

import com.example.practice.dto.UsersItemDto;
import com.example.practice.dto.UsersItemKeeperDto;
import com.example.practice.service.UsersItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/itemsManagement")
public class UsersItemController {

    private final UsersItemService usersItemService;

    @PostMapping
    public ResponseEntity<Void> addItemToUser(@RequestBody UsersItemKeeperDto usersItemKeeperDto) {
        usersItemService.addItemToUser(usersItemKeeperDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<UsersItemDto>> getAll() {
        return ResponseEntity.ok(usersItemService.readAll());
    }

    @DeleteMapping
    public ResponseEntity<Void> removeItemFromUser(@RequestBody UsersItemKeeperDto usersItemKeeperDto) {
        usersItemService.removeItemFromUser(usersItemKeeperDto);
        return ResponseEntity.ok().build();
    }

//    public ResponseEntity<List<Item>> getUserItems(User user) {
//        return ResponseEntity.ok(usersItemService.getUserItems(user));
//    }
}
