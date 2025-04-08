package org.example.tmstrainingspring.services;

import org.example.tmstrainingspring.entities.UserModel;
import org.example.tmstrainingspring.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void getAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(new UserModel()));
        Assertions.assertEquals(userService.findAll(), List.of(new UserModel()));
    }

    @Test
    void getUserById() {
        Mockito.when(userRepository.findById(2)).thenReturn(Optional.of(new UserModel()));
        Assertions.assertEquals(userService.findById(2), new UserModel());
    }
}
