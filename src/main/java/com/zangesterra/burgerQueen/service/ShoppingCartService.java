package com.zangesterra.burgerQueen.service;

import com.zangesterra.burgerQueen.dto.response.ShoppingCartResponse;
import com.zangesterra.burgerQueen.entity.Product;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface ShoppingCartService {

    ShoppingCartResponse getAllUserCartItems(String email);
    ShoppingCartResponse addToShoppingCart(Long productId, int amount, String email);
    ShoppingCartResponse addCartItemQuantity(Long productId, int amount, String email);
    ShoppingCartResponse subCartItemQuantity(Long productId, int amount, String email);
    ShoppingCartResponse removeCartItemFromShoppingCart(Long productId, String email);

}
