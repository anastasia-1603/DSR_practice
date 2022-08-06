package com.example.practice.mapper;

import com.example.practice.dto.UserDto;
import com.example.practice.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromDto(UserDto userDto);

    UserDto toDto(User user);

    List<User> fromDto(List<UserDto> userDtoList);

    List<UserDto> toDto(List<User> users);
}
