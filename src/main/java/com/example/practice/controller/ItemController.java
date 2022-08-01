package com.example.practice.controller;

import com.example.practice.dto.ItemDto;
import com.example.practice.entity.Item;
import com.example.practice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") Long id) {
        Optional<Item> optItem = itemService.read(id);

        return optItem.isPresent()
                ? new ResponseEntity<>(optItem.get(), HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Void> createItem(Item item) {
        itemService.create(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ItemDto>> readAll() {
        final List<ItemDto> items = itemService.readAll();

        return items != null && !items.isEmpty()
                ? new ResponseEntity<>(items, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody Item item) {
        boolean updated = itemService.update(item);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
