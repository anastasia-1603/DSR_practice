package com.example.practice.service;

import com.example.practice.dto.ItemDto;
import com.example.practice.dto.SearchItemDto;
import com.example.practice.entity.Item;
import com.example.practice.exceptions.ItemNotFoundException;
import com.example.practice.mapper.CategoryMapper;
import com.example.practice.mapper.ItemMapper;
import com.example.practice.repository.ItemRepository;
import com.example.practice.specifications.ItemSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final CategoryMapper categoryMapper;

    public void createItem(ItemDto itemDto) {
        itemRepository.save(itemMapper.fromDto(itemDto));
    }

    public ItemDto readItem(Long id) {
        Item item = itemRepository.findById((id)).orElseThrow(ItemNotFoundException::new);
        return itemMapper.toDto(item);
    }

    public void updateItem(ItemDto itemDto) {
        Item item = itemRepository.findById(itemDto.getId()).orElseThrow(ItemNotFoundException::new);
        item.setName(itemDto.getName());
        item.setCategory(categoryMapper.fromDto(itemDto.getCategory()));
        item.setCode(itemDto.getCode());
        item.setDescription(itemDto.getDescription());
        item.setImage(itemDto.getImage());
        itemRepository.save(item);
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

    public List<ItemDto> getAllItemsByCriteria(SearchItemDto searchItemDto){
        Specification<Item> productSpecification = ItemSpecification.getItemSpecification(searchItemDto);
        return itemRepository.findAll(productSpecification).stream().map(itemMapper::toDto).toList();
    }
}
