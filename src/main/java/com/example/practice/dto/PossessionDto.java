package com.example.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PossessionDto {

    @Null
    private Long id;

    @NotNull
    private UserDto user;

    @NotNull
    private ItemDto item;

    @NotNull
    private Instant withDate;

}
