package com.example.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchItemDto {
    private String name;
    private SearchUserDto user;
    private String categoryName;

    @Null
    private List<Long> childCategoriesIds;
}
