package com.example.practice.mapper;


import com.example.practice.dto.CategoryDto;
import com.example.practice.dto.CategoryViewDto;
import com.example.practice.dto.NewItemDto;
import com.example.practice.entity.Category;
import com.example.practice.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category fromDto(CategoryDto categoryDto);

    //    @Mapping(target = "category.parentCategory", ignore = true)
    CategoryDto toDto(Category category);

    List<Category> fromDto(List<CategoryDto> categoryDtoList);

    List<CategoryDto> toDto(List<Category> categories);

//    @Mapping(source = "id", target = "categoryViewDto.id")
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "parentCategoryId", target = "")
//    @Mapping(source = "newItemDto.description", target = "description")
//    CategoryViewDto toDto(Category category); // todo
}
