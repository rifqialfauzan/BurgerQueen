package com.zangesterra.burgerQueen.controller;

import com.zangesterra.burgerQueen.dto.request.RegisterUserRequest;
import com.zangesterra.burgerQueen.dto.response.UserResponse;
import com.zangesterra.burgerQueen.dto.request.UpdateUserRequest;
import com.zangesterra.burgerQueen.dto.response.WebResponse;
import com.zangesterra.burgerQueen.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping()
    public ResponseEntity<WebResponse<UserResponse>> getUserByEmail(Principal principal){
        UserResponse user = userService.getUser(principal.getName());
        return new ResponseEntity<>(WebResponse.<UserResponse>builder().data(user).build(), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<WebResponse<UserResponse>> updateUser(@RequestBody UpdateUserRequest request, Principal principal){
        UserResponse user = userService.updateUser(request, principal.getName());
        return new ResponseEntity<>(WebResponse.<UserResponse>builder().data(user).build(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<WebResponse<UserResponse>> registerUser(@RequestBody RegisterUserRequest request){
        UserResponse userResponse = userService.registerUser(request);
        return new ResponseEntity<>(WebResponse.<UserResponse>builder().data(userResponse).build(), HttpStatus.CREATED);
    }

}

