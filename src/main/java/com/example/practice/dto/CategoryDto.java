package com.example.practice.dto;

import com.example.practice.entity.Item;
import com.example.practice.validator.New;
import com.example.practice.validator.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String image;

    private Long parentCategoryId;

    @NotNull
    private Long code;



}
