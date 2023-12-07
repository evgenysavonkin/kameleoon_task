package com.evgenysav.kameleoon.controller;

import com.evgenysav.kameleoon.entity.User;
import com.evgenysav.kameleoon.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class MainController {

    private final UserService service;

    public MainController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String helloMsg(){
        return "hello";
    }

    @GetMapping("/create-person")
    public User createUser(){
        User user = service.save(new User("user1", "evgenysav@gmail.com", "qwerty123", new Date()));
        System.out.println(user);
        return user;
    }
}
