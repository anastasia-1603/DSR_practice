package com.example.practice.service;

import com.example.practice.dto.ItemDto;
import com.example.practice.dto.PossessionDto;
import com.example.practice.entity.Item;
import com.example.practice.entity.User;

import com.example.practice.exceptions.ItemNotFoundException;
import com.example.practice.exceptions.UserNotFoundException;
import com.example.practice.mapper.ItemMapper;
import com.example.practice.repository.ItemRepository;
import com.example.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserItemService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final PossessionService possessionService;
    private final ItemMapper itemMapper;

    public void addItemToUser(Long itemId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        possessionService.addPossession(user, item);
    }

    public List<ItemDto> getUserItems(Long userId) {
        return possessionService.getPossessionsByUserId(userId)
                .stream()
                .map(PossessionDto::getItem)
                .toList();
    }

    public void deleteUsersItem(Long itemId, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        if (!itemRepository.existsById(itemId)) {
            throw new ItemNotFoundException();
        }
        possessionService.deletePossession(userId, itemId);
    }

    public List<ItemDto> getItemsWithoutOwner() {
        return itemRepository.findAll()
                .stream()
                .filter(item -> item.getUser() == null)
                .map(itemMapper::toDto)
                .toList();
    }
}