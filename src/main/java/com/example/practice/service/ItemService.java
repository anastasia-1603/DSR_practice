package com.example.practice.service;

import com.example.practice.dto.ItemDto;
import com.example.practice.dto.NewItemDto;
import com.example.practice.dto.PageFilterSortItemDto;
import com.example.practice.entity.Category;
import com.example.practice.entity.Item;
import com.example.practice.exceptions.ItemNotFoundException;
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
    private final CategoryService categoryService;

    public void createItem(NewItemDto itemDto) {
        itemRepository.save(itemMapper.fromDto(itemDto,
                categoryService.getCategoryByName(itemDto.getCategoryName())));
    }

    public ItemDto getItemDtoById(Long id) {
        Item item = itemRepository.findById((id)).orElseThrow(ItemNotFoundException::new);
        return itemMapper.toDto(item);
    }

    public Item getItemById(Long id) {
        return itemRepository.findById((id)).orElseThrow(ItemNotFoundException::new);
    }

    public boolean existsById(Long id) {
        return itemRepository.existsById(id);
    }

    public List<ItemDto> getAllItemsByCategory(Category category) {
        return itemMapper.toDto(itemRepository.findAllByCategory(category));
    }

    public void updateItem(NewItemDto itemDto) {
        Item item = itemRepository.findById(itemDto.getId()).orElseThrow(ItemNotFoundException::new);
        item.setName(itemDto.getName());
        item.setCategory(categoryService.getCategoryByName(itemDto.getCategoryName()));
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

    public List<ItemDto> getAllItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return itemMapper.toDto(itemRepository.findAll(pageable).stream().toList());
    }

    public List<ItemDto> getAllItems() {
        return itemMapper.toDto(itemRepository.findAll());
    }

    public List<ItemDto> filterSortItemsPage(PageFilterSortItemDto itemDto) {
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
