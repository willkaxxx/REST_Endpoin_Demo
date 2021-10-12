package com.epam.training.demo.service;

import com.epam.training.demo.entity.User;
import com.epam.training.demo.exceptions.UserNotFoundException;
import com.epam.training.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.epam.training.demo.exceptions.UserAlreadyExistsException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User registration(User user) throws UserAlreadyExistsException {
        if (userRepo.findByName(user.getName()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        return userRepo.save(user);
    }

    public Iterable<User> getAll() {
        return userRepo.findAll();
    }

    public User getById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return user.get();
    }

    public User update(User user) throws UserNotFoundException {
        if (userRepo.findById(user.getId()).isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return userRepo.save(user);
    }

    public Long deleteById(Long id) {
        userRepo.deleteById(id);
        return id;
    }
}
