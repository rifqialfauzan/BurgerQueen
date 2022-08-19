package com.zangesterra.burgerQueen.service;

import com.zangesterra.burgerQueen.entity.CartItem;
import com.zangesterra.burgerQueen.entity.Product;
import com.zangesterra.burgerQueen.entity.ShoppingCart;
import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.repository.CartItemRepository;
import com.zangesterra.burgerQueen.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShoppingCartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public List<CartItem> all(){
        return cartItemRepository.findAll();
    }

    public CartItem get(Long id){
        return cartItemRepository.findById(id).get();
    }

    public void delete(Long id){
        cartItemRepository.deleteById(id);
    }


    public ShoppingCart addToCart(Product product, int quantity, User user){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        ShoppingCart shoppingCart = user.getShoppingCart();

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }

        Set<CartItem> cartItems = shoppingCart.getCartItem();
        CartItem cartItem = findCartItem(cartItems, product.getId());
        if (cartItems == null) {
            cartItems = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setShoppingCart(shoppingCart);
                cartItem.setTotal(Double.valueOf(decimalFormat.format((cartItem.getProduct().getPrice() * cartItem.getQuantity()))));
                cartItems.add(cartItem);

                cartItemRepository.save(cartItem);
            }
        }else {
            if (cartItem == null){
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setShoppingCart(shoppingCart);
                cartItem.setTotal(Double.valueOf(decimalFormat.format((cartItem.getProduct().getPrice() * cartItem.getQuantity()))));

                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setTotal(Double.valueOf(decimalFormat.format((cartItem.getProduct().getPrice() * cartItem.getQuantity()))));

                cartItemRepository.save(cartItem);
            }
        }

        shoppingCart.setCartItem(cartItems);
        shoppingCart.setSubtotal(subtotal(cartItems));
        shoppingCart.setUser(user);

        return shoppingCartRepository.save(shoppingCart);
    }

    private CartItem findCartItem(Set<CartItem> cartItems, Long productId){
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;
        for (CartItem item: cartItems){
            if (item.getProduct().getId() == productId){
            cartItem = item;
            }
        }
        return cartItem;
    }

    private double subtotal(Set<CartItem> cartItems){
        double subtotal = 0.0;

        for (CartItem item : cartItems){
            subtotal += item.getProduct().getPrice() * item.getQuantity();
        }
        return subtotal;
    }

}
