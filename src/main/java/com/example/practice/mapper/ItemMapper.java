package com.example.practice.mapper;

import com.example.practice.dto.ItemDto;
import com.example.practice.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item fromDto(ItemDto itemDto);

    @Mapping(target = "category.parentCategory", ignore = true)
    ItemDto toDto(Item item);

    List<Item> fromDto(List<ItemDto> itemDtoList);

    List<ItemDto> toDto(List<Item> items);
}
