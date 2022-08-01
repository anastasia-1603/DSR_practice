package com.example.practice.controller;

import com.example.practice.dto.ItemDto;
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
    public ResponseEntity<ItemDto> getItemById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(itemService.readItem(id));
    }

    @PostMapping
    public ResponseEntity<Void> createItem(ItemDto itemDto) {
        itemService.createItem(itemDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<ItemDto>> readAllItems() {
        return ResponseEntity.ok(itemService.readAllItems());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItem(@RequestBody ItemDto itemDto) {
        itemService.updateItem(itemDto);
        return ResponseEntity.ok().build();
    }
}
