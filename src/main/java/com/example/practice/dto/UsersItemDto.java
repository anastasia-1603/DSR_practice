package com.example.practice.dto;

import com.example.practice.entity.Item;
import com.example.practice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersItemDto {

    private Long id;

    private UserDto user;

    private ItemDto item;

    private Instant withDate;

}
