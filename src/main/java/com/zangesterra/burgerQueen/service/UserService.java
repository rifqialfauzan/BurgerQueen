package com.zangesterra.burgerQueen.service;

import com.zangesterra.burgerQueen.dto.request.RegisterUserRequest;
import com.zangesterra.burgerQueen.dto.request.UpdateUserRequest;
import com.zangesterra.burgerQueen.dto.response.UserResponse;
import com.zangesterra.burgerQueen.entity.Role;
import com.zangesterra.burgerQueen.entity.ShoppingCart;
import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.repository.RoleRepository;
import com.zangesterra.burgerQueen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public List<User> allUser(){
        return userRepository.findAll();
    }

    public UserResponse getUser(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .image(user.getImage())
                .build();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow();

    }

    /*
        PR
        This is without validation. so if request properties are null, this code will continue to update user detail with null value
        So later must implement validation
    */
    public UserResponse updateUser(UpdateUserRequest request, String email){
        User user = userRepository.findByEmail(email).orElseThrow();

        if (Objects.nonNull(request.getName())){
            user.setName(request.getName());
        }

        if (Objects.nonNull(request.getImage())){
            user.setImage(request.getImage());
        }

        if (Objects.nonNull(request.getPassword())){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .image(user.getImage())
                .build();
    }

//    PR
//    This is without validation. so if request properties are null, this code will continue to update user detail with null value
//    So later must implement validation
//    BUG : This still can register user with email that already in db (existing user email)
    public UserResponse registerUser(RegisterUserRequest request){
        Role role = roleRepository.findByName("ROLE_USER");
        String image = "default-image.jpg";

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setImage(image);
        user.setShoppingCart(new ShoppingCart());
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);

        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .image(user.getImage())
                .build();
    }

}
