package com.example.practice.controller;

import com.example.practice.dto.ItemDto;
import com.example.practice.dto.PageFilterSortItemDto;
import com.example.practice.service.ItemService;
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
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(itemService.getItemDtoById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createItem(@Validated(New.class) @RequestBody ItemDto itemDto) {
        itemService.createItem(itemDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItems(@RequestParam(defaultValue = "0", name = "page") int page,
                                                     @RequestParam(defaultValue = "20", name = "size") int size) {
        return ResponseEntity.ok(itemService.readAllItems(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateItem(@Validated(Update.class) @RequestBody ItemDto itemDto) {
        itemService.updateItem(itemDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("filter")
    public ResponseEntity<List<ItemDto>> getAllItemsFiltered(@RequestBody PageFilterSortItemDto itemDto) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.filterSortItemsPage(itemDto));
    }

}
