package com.zangesterra.burgerQueen.controller;

import com.zangesterra.burgerQueen.dto.request.LoginRequest;
import com.zangesterra.burgerQueen.dto.response.WebResponse;
import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    final static Logger getLog = LoggerFactory.getLogger(AuthController.class);


    @CrossOrigin
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<String>> login(@RequestBody LoginRequest loginRequest){
        try {
//            Verify user
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            ));

            User u = new User();
            u.setEmail(loginRequest.getEmail());

//            Create new token for user
            String token = jwtUtil.generateToken(u);

//            Return token to client
            return new ResponseEntity<>(WebResponse.<String>builder().data(token).build(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(WebResponse.<String>builder().errors(e.getMessage()).build(), HttpStatus.UNAUTHORIZED);
        }
    }
}
