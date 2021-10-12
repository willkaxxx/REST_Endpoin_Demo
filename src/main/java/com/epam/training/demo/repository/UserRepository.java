package com.epam.training.demo.repository;

import com.epam.training.demo.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Profile("!cache")
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);
}
