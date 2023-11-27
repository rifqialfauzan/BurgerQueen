package com.zangesterra.burgerQueen.controller;

import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.repository.UserRepository;
import com.zangesterra.burgerQueen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.allUser();
    }

    @PostMapping("update")
    public String updateUser(@ModelAttribute User user){
        userService.updateUser(user);
        return "redirect:/profile";
    }

    @PostMapping
    public void addUser( User user){
        userService.addUser(user);
    }

}

