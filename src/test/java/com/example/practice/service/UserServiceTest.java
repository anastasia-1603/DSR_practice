package com.example.practice.service;

import com.example.practice.dto.UserDto;
import com.example.practice.entity.User;
import com.example.practice.exceptions.UserEmailExistsException;
import com.example.practice.mapper.UserMapper;
import com.example.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.constraints.AssertTrue;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper userMapper;

    private final UserService userService;

    @Test
    public void createUserTest() {
        UserDto user = new UserDto(0L, "Гуреев", "Павел", "Федорович", "000@mail.ru");
        Mockito.when(userRepository.existsByEmail("000@mail.ru")).thenReturn(true);
        Assertions.assertThrows(UserEmailExistsException.class, () -> userService.createUser(user) );
    }

    @Test
    public void updateUserTest() {
        Mockito.when(userRepository.findById(0L)).thenReturn(Optional.of(new User()));
        UserDto user = new UserDto(0L, "Гуреев", "Павел", "Федорович", "01@mail.ru");
        userService.updateUser(user);
    }

    @Test
    public void getUserByIdTest() {
        Mockito.when(userRepository.findById(0L)).thenReturn(Optional.of(new User()));
        UserDto user = new UserDto(0L, "Гуреев", "Павел", "Федорович", "01@mail.ru");
        userService.updateUser(user);
    }

}
