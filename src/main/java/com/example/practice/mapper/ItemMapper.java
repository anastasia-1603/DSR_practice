package com.example.practice.mapper;

import com.example.practice.dto.ItemDto;
import com.example.practice.dto.NewItemDto;
import com.example.practice.entity.Category;
import com.example.practice.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item fromDto(ItemDto itemDto);

    ItemDto toDto(Item item);

    List<Item> fromDto(List<ItemDto> itemDtoList);

    List<ItemDto> toDto(List<Item> items);

    @Mapping(source = "newItemDto.id", target = "id")
    @Mapping(source = "newItemDto.name", target = "name")
    @Mapping(source = "newItemDto.image", target = "image")
    @Mapping(source = "newItemDto.description", target = "description")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "newItemDto.code", target = "code")
    Item fromDto(NewItemDto newItemDto, Category category);

    @Mapping(source = "item.id", target = "id")
    @Mapping(source = "item.name", target = "name")
    @Mapping(source = "item.image", target = "image")
    @Mapping(source = "item.description", target = "description")
    @Mapping(source = "item.category.name", target = "categoryName")
    @Mapping(source = "item.code", target = "code")
    NewItemDto toNewItemDto(Item item);
}
