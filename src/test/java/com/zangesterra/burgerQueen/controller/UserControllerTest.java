package com.zangesterra.burgerQueen.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zangesterra.burgerQueen.dto.response.UserResponse;
import com.zangesterra.burgerQueen.dto.response.WebResponse;
import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.repository.UserRepository;
import com.zangesterra.burgerQueen.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

//    @Autowired
//    private WebApplicationContext wac;
//
//    @BeforeEach
//    void setUp() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//    }

    @Test
    void getUserUnauthorized() throws Exception {
        mockMvc.perform(
                get("/user")
        ).andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    void getUserSuccess() throws Exception {

        User user = new User();
        user.setEmail("pastilolos@tokopedia.com");
        user.setPassword("password");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());

        String token = jwtUtil.generateToken(user);

        mockMvc.perform(
                get("/user")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(
                status().isOk()
        ).andDo(result -> {
            WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            Assertions.assertNull(response.getErrors());
            assertEquals("pastilolos@tokopedia.com", response.getData().getEmail());
        });
    }
}