package com.example.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageFilterSortItemDto {

    private String name;
    private SearchUserDto user;
    private String categoryName;

    @Null
    private List<Long> childCategoriesIds;

    @Min(0)
    private int page = 0;

    @Min(1)
    private int size = 20;

    private SortItemDto sort;
}
