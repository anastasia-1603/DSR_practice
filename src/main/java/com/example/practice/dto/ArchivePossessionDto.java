package com.example.practice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivePossessionDto {

    @Null
    private Long id;

    @NotNull
    private UserDto user;

    @NotNull
    private ItemDto item;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant withDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant toDate;
}
