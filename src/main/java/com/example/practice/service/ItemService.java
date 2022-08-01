package com.example.practice.service;

import com.example.practice.dto.ItemDto;
import com.example.practice.entity.Item;
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

    public Optional<Item> read(Long id) {
        return itemRepository.findById((id));
    }

    public void create(Item item) {
        itemRepository.save(item);
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    public List<ItemDto> readAll() {
        var t = itemRepository.findAll();
        var mt = itemMapper.toDto(t);
        return mt;
    }

    public boolean update(Item item) {
        if (itemRepository.existsById(item.getId())) {
            itemRepository.save(item);
            return true;
        }
        return false;
    }

}
