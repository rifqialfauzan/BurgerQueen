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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
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

    public UserResponse registerUser(@Valid RegisterUserRequest request){
        Role role = roleRepository.findByName("ROLE_USER");
        String image = "default-image.jpg";

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already taken");
        }

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
