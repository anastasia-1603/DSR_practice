package com.example.practice.service;

import com.example.practice.dto.UserDto;
import com.example.practice.exceptions.UserExistsException;
import com.example.practice.exceptions.UserNotFoundException;
import com.example.practice.mapper.UserMapper;
import com.example.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void createUser(UserDto userDto) {
        userRepository.save(userMapper.fromDto(userDto));
    }

    public UserDto readUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        return userMapper.toDto(userRepository.findById((id)).get());
    }

    public void updateUser(UserDto userDto) {
        if (!userRepository.existsById(userDto.getId())) {
            throw new UserNotFoundException();
        }
        userRepository.save(userMapper.fromDto(userDto));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    public List<UserDto> readAllUsers() {
        return userMapper.toDto(userRepository.findAll());
    }

}
