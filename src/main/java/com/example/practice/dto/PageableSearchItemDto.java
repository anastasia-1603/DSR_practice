package com.example.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableSearchItemDto {

    private String name;
    private SearchUserDto user;
    private String categoryName;

    @Null
    private List<Long> childCategoriesIds;

    @NotNull
    @Min(0)
    private int page;

    @NotNull
    @Min(1)
    private int size;
}
