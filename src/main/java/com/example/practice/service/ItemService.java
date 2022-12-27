package com.example.practice.service;

import com.example.practice.dto.ItemDto;
import com.example.practice.dto.NewItemDto;
import com.example.practice.dto.PageFilterSortItemDto;
import com.example.practice.dto.UserDto;
import com.example.practice.entity.Category;
import com.example.practice.entity.Item;
import com.example.practice.exceptions.ItemNotFoundException;
import com.example.practice.mapper.ItemMapper;
import com.example.practice.repository.ItemRepository;
import com.example.practice.specifications.ItemSpecification;
import com.example.practice.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    public Long getCategoryIdOfItem(Long itemId) {
        return getItemById(itemId).getCategory().getId();
    }

    private void setImageToItem(Long itemId, MultipartFile multipartFile){
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        if (multipartFile == null || multipartFile.isEmpty()) {
            item.setImage("img_placeholder.png");
        } else {
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String uploadDir = "src/main/resources/static/img";
            FileUtil.saveFile(uploadDir, filename, multipartFile); //todo разобраться мб можно упростить
            item.setImage(filename);
        }
    }

    public void createItem(String name, String description, String categoryName,
                           MultipartFile multipartFile, Long code) {
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setCategory(categoryService.getCategoryByName(categoryName));
        item.setCode(code);
        itemRepository.save(item);
        Long id = item.getId();
        setImageToItem(id, multipartFile);
    }

    public ItemDto getItemDtoById(Long id) {
        Item item = itemRepository.findById((id)).orElseThrow(ItemNotFoundException::new);
        return itemMapper.toDto(item);
    }

    public NewItemDto getNewItemDtoById(Long id) {
        Item item = itemRepository.findById((id)).orElseThrow(ItemNotFoundException::new);
        return itemMapper.toNewItemDto(item);
    }

    public Item getItemById(Long id) {
        return itemRepository.findById((id)).orElseThrow(ItemNotFoundException::new);
    }

    public boolean existsById(Long id) {
        return itemRepository.existsById(id);
    }

    public List<ItemDto> getAllItemsByCategory(Category category) {
        List<Category> childCategories = categoryService.getChildCategories(category.getId());
        List<Item> items = itemRepository.findAllByCategoryIn(childCategories);
        return itemMapper.toDto(items);
    }

    public Page<ItemDto> getAllItemsByCategoryPaginated(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        if (categoryId == null || categoryId == -1) {
            return itemRepository.findAll(pageable).map(itemMapper::toDto);
        }
        List<Category> childCategories = categoryService.getChildCategories(categoryId);
        Page<Item> items = itemRepository.findAllByCategoryIn(childCategories, pageable);
        return items.map(itemMapper::toDto);
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

    public void updateItem(Long itemId, String name, String description, String categoryName,
                           MultipartFile multipartFile, Long code) {
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        item.setName(name);
        item.setCategory(categoryService.getCategoryByName(categoryName));
        item.setCode(code);
        item.setDescription(description);
        if (multipartFile != null && !multipartFile.isEmpty()) {
            setImageToItem(itemId, multipartFile);
        }

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

    public Page<ItemDto> getPageItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return itemRepository.findAll(pageable).map(itemMapper::toDto);
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
