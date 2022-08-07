package com.example.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

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
    @Value("0")
    private int page;

    @Min(1)
    @Value("20")
    private int size;

    private SortItemDto sort;
}
