package com.example.practice.controller;

import com.example.practice.dto.PossessionDto;
import com.example.practice.dto.UsersItemIdsDto;
import com.example.practice.service.PossessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/possession")
public class PossessionController {

    private final PossessionService possessionService;

    @PostMapping
    public ResponseEntity<Void> addItemToUser(@RequestBody UsersItemIdsDto usersItemIdsDto) {
        possessionService.addItemToUser(usersItemIdsDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<PossessionDto>> getAll() {
        return ResponseEntity.ok(possessionService.readAll());
    }

    @DeleteMapping
    public ResponseEntity<Void> removeItemFromUser(@RequestBody UsersItemIdsDto usersItemIdsDto) {
        possessionService.removeItemFromUser(usersItemIdsDto);
        return ResponseEntity.ok().build();
    }

//    public ResponseEntity<List<Item>> getUserItems(User user) {
//        return ResponseEntity.ok(usersItemService.getUserItems(user));
//    }
}
