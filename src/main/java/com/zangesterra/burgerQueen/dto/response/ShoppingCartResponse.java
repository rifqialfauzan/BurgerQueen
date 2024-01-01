package com.zangesterra.burgerQueen.dto.response;

import com.zangesterra.burgerQueen.entity.CartItem;
import com.zangesterra.burgerQueen.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartResponse {

    private Long id;

    private Set<CartItemResponse> cartItems;

    private Double totalPrice;

}

