package com.evgenysav.kameleoon.service;

import com.evgenysav.kameleoon.entity.User;
import com.evgenysav.kameleoon.repository.UserRepository;
import com.evgenysav.kameleoon.util.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User createUser(User user) {
        repository.save(user);
        return user;
    }

    public User findUserById(long userId) {
        Optional<User> userOpt = repository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        return userOpt.get();
    }

    @Transactional
    public void saveUser(User user) {
        repository.save(user);
    }
}
