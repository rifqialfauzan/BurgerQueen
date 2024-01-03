package com.zangesterra.burgerQueen.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {

    @NotBlank(message = "Email can not be null")
    @Email(message = "Please insert valid email")
    private String email;

    @NotBlank(message = "Name can not be null")
    private String name;

    @NotBlank(message = "password can not be null")
    @Size(min = 8, message = "Password must be at least 8 character")
    private String password;
}
