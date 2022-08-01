package com.example.practice.service;

import com.example.practice.dto.ItemDto;
import com.example.practice.exceptions.ItemExistsException;
import com.example.practice.exceptions.ItemNotFoundException;
import com.example.practice.mapper.ItemMapper;
import com.example.practice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public void createItem(ItemDto itemDto) {
        if (itemRepository.existsById(itemDto.getId())) {
            throw new ItemExistsException("Item with id = " + itemDto.getId() + "already exists");
        }
        itemRepository.save(itemMapper.fromDto(itemDto));
    }

    public ItemDto readItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ItemNotFoundException();
        }
        return itemMapper.toDto(itemRepository.findById((id)).get());
    }

    public void updateItem(ItemDto itemDto) {
        if (!itemRepository.existsById(itemDto.getId())) {
            throw new ItemNotFoundException();
        }
        itemRepository.save(itemMapper.fromDto(itemDto));
    }

    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ItemNotFoundException();
        }
        itemRepository.deleteById(id);
    }

    public List<ItemDto> readAllItems() {
        return itemMapper.toDto(itemRepository.findAll());
    }
}
