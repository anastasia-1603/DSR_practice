package com.example.practice.mapper;

import com.example.practice.dto.UserDto;
import com.example.practice.dto.UsersItemDto;
import com.example.practice.entity.User;
import com.example.practice.entity.UsersItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersItemMapper {

    UsersItem fromDto(UsersItemDto usersItemDto);

    UsersItemDto toDto(UsersItem usersItem);

//    UserDto toDto(User user);

    List<UsersItem> fromDto(List<UsersItemDto> UsersItemDtoList);

    List<UsersItemDto> toDto(List<UsersItem> usersItemList);

}
