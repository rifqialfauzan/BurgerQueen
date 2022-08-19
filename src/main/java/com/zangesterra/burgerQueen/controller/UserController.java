package com.zangesterra.burgerQueen.controller;

import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.repository.UserRepository;
import com.zangesterra.burgerQueen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.allUser();
    }

    @PutMapping
    public void updateUser(@RequestBody User user){
        userService.updateUser(user);
    }

    @PostMapping
    public void addUser( User user){
        userService.addUser(user);
    }

}

