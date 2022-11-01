package com.example.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryViewDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    private Long parentCategoryId;

    private List<CategoryViewDto> childCategories;
}
