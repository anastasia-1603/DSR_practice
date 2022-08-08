package com.example.practice.controller;

import com.example.practice.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @MockBean
    private UserRepository userRepository;


}
