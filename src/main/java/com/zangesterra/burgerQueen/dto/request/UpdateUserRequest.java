package com.zangesterra.burgerQueen.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {

    private String name;
    private String password;
    private String image;
}
