package com.example.practice.service;

import com.example.practice.entity.User;
import com.example.practice.exceptions.UserExistsException;
import com.example.practice.exceptions.UserNotFoundException;
import com.example.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void create(User user) {
        if (userRepository.existsById(user.getId())) {
            throw new UserExistsException("User with id = " + user.getId() + "already exists");
        }
        userRepository.save(user);
    }

    public Optional<User> read(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        return userRepository.findById((id));
    }

    public void update(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new UserNotFoundException();
        }
        userRepository.save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    public List<User> readAll() {
        return userRepository.findAll();
    }

}
