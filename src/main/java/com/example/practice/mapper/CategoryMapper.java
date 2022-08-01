package com.example.practice.mapper;


import com.example.practice.dto.CategoryDto;
import com.example.practice.entity.Category;
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
}
