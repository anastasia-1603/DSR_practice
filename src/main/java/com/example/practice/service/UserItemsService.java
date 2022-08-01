package com.example.practice.service;

import com.example.practice.dto.UserDto;
import com.example.practice.dto.UsersItemDto;
import com.example.practice.dto.UsersItemKeeperDto;
import com.example.practice.entity.Item;
import com.example.practice.entity.User;
import com.example.practice.entity.UsersItem;
import com.example.practice.exceptions.ItemNotFoundException;
import com.example.practice.exceptions.UserNotFoundException;
import com.example.practice.mapper.UsersItemMapper;
import com.example.practice.repository.ItemRepository;
import com.example.practice.repository.UserItemsRepository;
import com.example.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserItemsService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final UserItemsRepository userItemsRepository;
    private final UsersItemMapper usersItemMapper;

    public void addItemToUser(UsersItemKeeperDto usersItemKeeperDto) {
        Optional<User> user = userRepository.findById(usersItemKeeperDto.getUserId());
        Optional<Item> item = itemRepository.findById(usersItemKeeperDto.getItemId());
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        if (item.isEmpty()) {
            throw new ItemNotFoundException();
        }
        UsersItem usersItem = new UsersItem();
        usersItem.setUser(user.get());
        usersItem.setItem(item.get());
        usersItem.setWithDate(Instant.now());
        userItemsRepository.save(usersItem);

    }

    public List<UsersItemDto> readAll() {
        return usersItemMapper.toDto(userItemsRepository.findAll());
    }
}
