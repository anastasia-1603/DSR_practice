package com.example.practice.service;

import com.example.practice.entity.User;
import com.example.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> read(Long id) {
        return userRepository.findById((id));
    }

    public void create(User user) {
        userRepository.save(user);
    }

    public List<User> readAll() {
        return userRepository.findAll();
    }

    public boolean update(User user, Long id) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
