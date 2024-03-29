package com.zangesterra.burgerQueen.controller;

import com.zangesterra.burgerQueen.dto.response.ShoppingCartResponse;
import com.zangesterra.burgerQueen.dto.response.WebResponse;
import com.zangesterra.burgerQueen.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/testCart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<WebResponse<ShoppingCartResponse>> getAllUserCartItems(Principal principal){
        ShoppingCartResponse response = shoppingCartService.getAllUserCartItems(principal.getName());
        return new ResponseEntity<>(WebResponse.<ShoppingCartResponse>builder().data(response).build(), HttpStatus.OK);
    }

    @PostMapping("/addToCart")
    public ResponseEntity<WebResponse<ShoppingCartResponse>> addProductToCart(@RequestParam Long productId, @RequestParam int amount, Principal principal){
        ShoppingCartResponse response = shoppingCartService.addToShoppingCart(productId, amount, principal.getName());
        return new ResponseEntity<>(WebResponse.<ShoppingCartResponse>builder().data(response).build(), HttpStatus.OK);
    }

    @PostMapping("/addQuantity")
    public ResponseEntity<WebResponse<ShoppingCartResponse>> addCartItemQuantity(@RequestParam Long productId, @Value("1") int amount, Principal principal){
        ShoppingCartResponse response = shoppingCartService.addCartItemQuantity(productId, amount, principal.getName());
        return new ResponseEntity<>(WebResponse.<ShoppingCartResponse>builder().data(response).build(), HttpStatus.OK);
    }

    @PostMapping("/subQuantity")
    public ResponseEntity<WebResponse<ShoppingCartResponse>> subCartItemQuantity(@RequestParam Long productId, @Value("1") int amount, Principal principal){
        ShoppingCartResponse response = shoppingCartService.subCartItemQuantity(productId, amount, principal.getName());
        return new ResponseEntity<>(WebResponse.<ShoppingCartResponse>builder().data(response).build(), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<WebResponse<ShoppingCartResponse>> deleteCartItem(@PathVariable Long productId, Principal principal){
        ShoppingCartResponse response = shoppingCartService.removeCartItemFromShoppingCart(productId, principal.getName());
        return new ResponseEntity<>(WebResponse.<ShoppingCartResponse>builder().data(response).build(), HttpStatus.OK);
    }
}
