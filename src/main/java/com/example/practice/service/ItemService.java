package com.example.practice.service;

import com.example.practice.dto.ItemDto;
import com.example.practice.dto.PageableSearchItemDto;
import com.example.practice.dto.SearchItemDto;
import com.example.practice.entity.Item;
import com.example.practice.exceptions.ItemNotFoundException;
import com.example.practice.mapper.CategoryMapper;
import com.example.practice.mapper.ItemMapper;
import com.example.practice.repository.ItemRepository;
import com.example.practice.specifications.ItemSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

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
        item.setCategory(categoryService.readCategory(itemDto.getCategory().getId()));
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

    public List<ItemDto> readAllItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return itemMapper.toDto(itemRepository.findAll(pageable).stream().toList());
    }

//    public List<ItemDto> filterItems(SearchItemDto itemDto) {
//        List<Long> ids = categoryService.getChildCategoriesIds(itemDto.getCategoryName());
//        itemDto.setChildCategoriesIds(ids);
//        Specification<Item> productSpecification = ItemSpecification.getItemSpecification(itemDto);
//        return itemRepository.findAll(productSpecification).stream().map(itemMapper::toDto).toList();
//    }

    public List<ItemDto> filterItemsPage(PageableSearchItemDto itemDto) {
        if (itemDto.getPage() < 0 || itemDto.getSize() < 0) {
            return Collections.emptyList();
        }
        Pageable pageable = PageRequest.of(itemDto.getPage(), itemDto.getSize());

        List<Long> ids = categoryService.getChildCategoriesIds(itemDto.getCategoryName());
        itemDto.setChildCategoriesIds(ids);

        Specification<Item> productSpecification = ItemSpecification.getItemSpecification(itemDto);
        return itemRepository.findAll(productSpecification, pageable).stream().map(itemMapper::toDto).toList();
    }

}
