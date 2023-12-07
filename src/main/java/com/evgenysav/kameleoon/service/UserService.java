package com.evgenysav.kameleoon.service;

import com.evgenysav.kameleoon.entity.User;
import com.evgenysav.kameleoon.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User save(User user) {
        repository.save(user);
        return user;
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(long id){
        return repository.findById(id).orElse(new User());
    }

    @Transactional
    public void update(User user){
        repository.save(user);
    }

    @Transactional
    public void delete(User user){
        repository.delete(user);
    }
}
