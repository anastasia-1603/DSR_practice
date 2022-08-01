package com.example.practice.service;

import com.example.practice.dto.ItemDto;
import com.example.practice.entity.Item;
import com.example.practice.exceptions.ItemExistsException;
import com.example.practice.exceptions.ItemNotFoundException;
import com.example.practice.mapper.ItemMapper;
import com.example.practice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public void create(Item item) {
        if (itemRepository.existsById(item.getId())) {
            throw new ItemExistsException("Item with id = " + item.getId() + "already exists");
        }
        itemRepository.save(item);
    }

    public Optional<Item> read(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ItemNotFoundException();
        }
        return itemRepository.findById((id));
    }

    public void update(Item item) {
        if (!itemRepository.existsById(item.getId())) {
            throw new ItemNotFoundException();
        }
        itemRepository.save(item);
    }

    public void delete(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ItemNotFoundException();
        }
        itemRepository.deleteById(id);
    }

    public List<ItemDto> readAll() {
        return itemMapper.toDto(itemRepository.findAll());
    }
}
