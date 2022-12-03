package com.example.practice.service;

import com.example.practice.dto.ItemDto;
import com.example.practice.dto.UserDto;
import com.example.practice.entity.Item;
import com.example.practice.entity.User;
import com.example.practice.exceptions.ItemNotFoundException;
import com.example.practice.exceptions.UserEmailExistsException;
import com.example.practice.exceptions.UserNotFoundException;
import com.example.practice.mapper.UserMapper;
import com.example.practice.repository.ItemRepository;
import com.example.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ItemService itemService;
    private final PossessionService possessionService;
    private final ItemRepository itemRepository;

    public void createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserEmailExistsException();
        }
        userRepository.save(userMapper.fromDto(userDto));
    }

    public void createUser(String surname, String name, String patronymic, String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserEmailExistsException();
        }
        UserDto userDto = new UserDto();
        userDto.setSurname(surname);
        userDto.setName(name);
        userDto.setPatronymic(patronymic);
        userDto.setEmail(email);
        userRepository.save(userMapper.fromDto(userDto));
    }

    public UserDto getUserById(Long id) {
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

    public void updateUser(Long userId, String surname, String name, String patronymic, String email) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setName(name);
        user.setPatronymic(patronymic);
        user.setSurname(surname);
        user.setEmail(email);
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

    public List<UserDto> readAllUsersSortedById() {
        return userMapper.toDto(userRepository.findAllByOrderByIdAsc());
    }

    public List<UserDto> readAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userMapper.toDto(userRepository.findAll(pageable).stream().toList());
    }

    public Page<UserDto> getPageUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    public Integer getTotalPages(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size)).getTotalPages();
    }

    public List<Integer> getPageNumbers(int page, int size) {
        int totalPages = userRepository.findAll(PageRequest.of(page, size)).getTotalPages();
        return IntStream.rangeClosed(0, totalPages).boxed().toList();
    }

    public void addItemToUser(Long userId, Long itemId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        possessionService.addPossession(user, item);
    }

    public List<ItemDto> getUserItems(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        return possessionService.getUserItems(userId);
    }

//    public List<ItemDto> getAllItemsWithOwner() {
//        return possessionService.
//    }

    public void deleteUsersItem(Long userId, Long itemId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        if (!itemService.existsById(itemId)) {
            throw new ItemNotFoundException();
        }
        possessionService.deletePossession(userId, itemId);
    }

}
