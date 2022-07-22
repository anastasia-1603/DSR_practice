package com.example.practice.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    private String surname;

    @NotNull
    private String name;

    @NotNull
    private String patronymic;

    @NotNull
    private String email;
}
