package com.example.practice.dto;

import com.example.practice.validator.New;
import com.example.practice.validator.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    private Long id;

    @NotNull
    private String surname;

    @NotNull
    private String name;

    @NotNull
    private String patronymic;

    @Email
    @NotEmpty
    @NotNull
    private String email;

    private List<ItemDto> items;
}
