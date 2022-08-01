package com.example.practice.controller;

import com.example.practice.dto.ItemDto;
import com.example.practice.entity.Item;
import com.example.practice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(itemService.read(id).get());
    }

    @PostMapping
    public ResponseEntity<Void> createItem(Item item) {
        itemService.create(item);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<ItemDto>> readAll() {
        return ResponseEntity.ok(itemService.readAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItem(@RequestBody Item item) {
        itemService.update(item);
        return ResponseEntity.ok().build();
    }
}
