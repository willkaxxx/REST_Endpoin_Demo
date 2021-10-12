package com.epam.training.demo.controllers;

import com.epam.training.demo.entity.User;
import com.epam.training.demo.exceptions.UserAlreadyExistsException;
import com.epam.training.demo.exceptions.UserNotFoundException;
import com.epam.training.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User registration(@RequestBody User user) throws UserAlreadyExistsException {
        return userService.registration(user);
    }

    @PatchMapping
    public User update(@RequestBody User user) throws UserNotFoundException {
            return userService.update(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) throws UserNotFoundException {
            return userService.getById(id);
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
            return userService.getAll();
    }

    @DeleteMapping
    public Long delete(@RequestParam Long id) {
            return userService.deleteById(id);
    }

}
