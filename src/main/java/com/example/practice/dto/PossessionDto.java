package com.example.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PossessionDto {

    private Long id;

    private UserDto user;

    private ItemDto item;

    private Instant withDate;

}
