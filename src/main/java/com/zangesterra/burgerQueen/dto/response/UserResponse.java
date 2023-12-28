package com.zangesterra.burgerQueen.dto.response;

import com.zangesterra.burgerQueen.entity.Role;
import com.zangesterra.burgerQueen.entity.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String email;

    private String name;

    private String image;


}
