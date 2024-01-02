package com.zangesterra.burgerQueen.service;

import com.zangesterra.burgerQueen.dto.response.ShoppingCartResponse;
import org.springframework.stereotype.Service;

@Service
public interface ShoppingCartService {

    ShoppingCartResponse getAllUserCartItems(String email);
    ShoppingCartResponse addToShoppingCart(Long productId, int amount, String email);
    ShoppingCartResponse addCartItemQuantity(Long productId, int amount, String email);
    ShoppingCartResponse subCartItemQuantity(Long productId, int amount, String email);
    ShoppingCartResponse removeCartItemFromShoppingCart(Long productId, String email);

}
