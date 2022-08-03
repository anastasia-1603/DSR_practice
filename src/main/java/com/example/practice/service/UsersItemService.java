package com.example.practice.service;

import com.example.practice.dto.UsersItemDto;
import com.example.practice.dto.UsersItemKeeperDto;
import com.example.practice.entity.ArchiveUsersItem;
import com.example.practice.entity.Item;
import com.example.practice.entity.User;
import com.example.practice.entity.UsersItem;
import com.example.practice.exceptions.ItemNotFoundException;
import com.example.practice.exceptions.UserNotFoundException;
import com.example.practice.exceptions.UsersItemNotFoundException;
import com.example.practice.mapper.UsersItemMapper;
import com.example.practice.repository.ArchiveUsersItemRepository;
import com.example.practice.repository.ItemRepository;
import com.example.practice.repository.UsersItemRepository;
import com.example.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final UsersItemRepository usersItemRepository;
    private final UsersItemMapper usersItemMapper;
    private final ArchiveUsersItemRepository archiveUsersItemRepository;

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
        usersItemRepository.save(usersItem);

    }

    public List<UsersItemDto> readAll() {
        return usersItemMapper.toDto(usersItemRepository.findAll());
    }

    public void removeItemFromUser(UsersItemKeeperDto uikd) {
        UsersItem usersItem = usersItemRepository.findByUserIdAndItemId(uikd.getUserId(), uikd.getItemId());
        ArchiveUsersItem archiveUsersItem = new ArchiveUsersItem();
        archiveUsersItem.setUser(usersItem.getUser());
        archiveUsersItem.setItem(usersItem.getItem());
        archiveUsersItem.setWithDate(usersItem.getWithDate());
        archiveUsersItem.setToDate(Instant.now());
        archiveUsersItemRepository.save(archiveUsersItem);
        usersItemRepository.delete(usersItem);
    }
}
