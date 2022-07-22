package com.example.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private Long id;
    private String name;
    private String description;
    private String image;
    private Long categoryId;

}