package com.example.practice.dto;

import com.example.practice.validator.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

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

//    private List<Possession> items;
//    private List<ArchivePossession> archive;
}
