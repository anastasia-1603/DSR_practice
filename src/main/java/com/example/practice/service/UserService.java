package com.example.practice.service;

import com.example.practice.dto.ItemDto;
import com.example.practice.dto.UserDto;
import com.example.practice.entity.User;
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
    private final ItemService itemService;
    private final PossessionService possessionService;

    public void createUser(UserDto userDto) {
        userRepository.save(userMapper.fromDto(userDto));
    }

    public UserDto readUser(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public void updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(UserNotFoundException::new);
        user.setName(userDto.getName());
        user.setPatronymic(userDto.getPatronymic());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
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

    public void addItemToUser(Long userId, Long itemId) {
        UserDto user = readUser(userId);
        ItemDto item = itemService.readItem(itemId);
        possessionService.addPossession(user, item);
    }

    public List<ItemDto> getUserItems(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        return possessionService.getUserItems(userId);
    }

}
