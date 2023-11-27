package com.zangesterra.burgerQueen.service;

import com.zangesterra.burgerQueen.entity.Role;
import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.repository.RoleRepository;
import com.zangesterra.burgerQueen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> allUser(){
        return userRepository.findAll();
    }

    public User getUser(Long id){
        return userRepository.findById(id).get();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).get();
    }

    public void updateUser(User user){

        User find = userRepository.findById(user.getId()).get();

        find.setName(user.getName());
//        find.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(find);
    }

    public void addUser(User user){
        Role role = roleRepository.findByName("ROLE_USER");
        String image = "default-image.jpg";

        User create = new User();
        create.setName(user.getName());
        create.setEmail(user.getEmail());
        create.setPassword(passwordEncoder.encode(user.getPassword()));

        create.setImage(image);
        create.setRoles(Collections.singleton(role));
        userRepository.save(create);
    }

}
