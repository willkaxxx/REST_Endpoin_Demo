package com.epam.training.demo.serviceTests;

import com.epam.training.demo.entity.User;
import com.epam.training.demo.exceptions.UserAlreadyExistsException;
import com.epam.training.demo.exceptions.UserNotFoundException;
import com.epam.training.demo.repository.UserRepository;
import com.epam.training.demo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class UserServiceTest {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserService userService = new UserService(userRepository);
    private static final User USER = new User();

    @BeforeAll
    static void prepare(){
        USER.setName("MyNewUser");
        USER.setId(2L);
    }

    @Test
    void registrationFailTest() {
        Mockito.when(userRepository.findByName(USER.getName())).thenReturn(Optional.of(USER));
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registration(USER));
    }

    @Test
    void registrationPassTest() throws UserAlreadyExistsException {
        Mockito.when(userRepository.findByName(USER.getName())).thenReturn(Optional.empty());
        userService.registration(USER);
    }
    @Test
    void getByIdFailTest() {
        Mockito.when(userRepository.findById(USER.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getById(USER.getId()));
    }

    @Test
    void getByIdPassTest() throws UserNotFoundException {
        Mockito.when(userRepository.findById(USER.getId())).thenReturn(Optional.of(USER));
        Assertions.assertEquals(USER, userService.getById(USER.getId()));
    }
    @Test
    void updateFailTest() {
        Mockito.when(userRepository.findById(USER.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.update(USER));
    }

    @Test
    void updatePassTest() throws UserNotFoundException {
        Mockito.when(userRepository.findById(USER.getId())).thenReturn(Optional.of(USER));
        Mockito.when(userRepository.save(USER)).thenReturn(USER);
        Assertions.assertEquals(USER, userService.update(USER));
    }
    @Test
    void deleteByIdPassTest() {
        userService.deleteById(USER.getId());
        Mockito.verify(userRepository).deleteById(USER.getId());
    }
    @Test
    void getAllPassTest() {
        userService.getAll();
        Mockito.verify(userRepository).findAll();
    }
}
