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
import java.util.Objects;
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

    DecimalFormat decimalFormat = new DecimalFormat("0.00");


    public ShoppingCart addToCart(Product product, int quantity, User user){
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
                cartItem.setTotal(Double.valueOf(decimalFormat.format(((long) cartItem.getProduct().getPrice() * cartItem.getQuantity()))));
                cartItems.add(cartItem);

                cartItemRepository.save(cartItem);
            }
        }else {
            if (cartItem == null){
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setShoppingCart(shoppingCart);
                cartItem.setTotal(Double.valueOf(decimalFormat.format(((long) cartItem.getProduct().getPrice() * cartItem.getQuantity()))));

                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setTotal(Double.valueOf(decimalFormat.format(((long) cartItem.getProduct().getPrice() * cartItem.getQuantity()))));

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
            if (Objects.equals(item.getProduct().getId(), productId)){
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

    public void addQuantity(Product product, User user) {
        if (user.getShoppingCart() == null){
            return;
        }
//        Ambil cart dari si user
        ShoppingCart shoppingCart = user.getShoppingCart();
//        Ambil semua item dari cartnya si user
        Set<CartItem> cartItems = shoppingCart.getCartItem();
//        Pilih spesifik item dari semua item di cart user dengan product id
        CartItem cartItem = findCartItem(cartItems, product.getId());
//        Set quantity-nya
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItem.setTotal(Double.valueOf(decimalFormat.format(((long) cartItem.getProduct().getPrice() * cartItem.getQuantity()))));
        cartItemRepository.save(cartItem);
        shoppingCart.setSubtotal(subtotal(cartItems));
        shoppingCartRepository.save(shoppingCart);
    }

    public void subQuantity(Product product, User user) {
        if (user.getShoppingCart() == null){
            return;
        }
//        Ambil cart dari si user
        ShoppingCart shoppingCart = user.getShoppingCart();
//        Ambil semua item dari cartnya si user
        Set<CartItem> cartItems = shoppingCart.getCartItem();
//        Pilih spesifik item dari semua item di cart user dengan product id
        CartItem cartItem = findCartItem(cartItems, product.getId());
//        Set quantity-nya
        if (cartItem.getQuantity() > 1){
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItem.setTotal(Double.valueOf(decimalFormat.format(((long) cartItem.getProduct().getPrice() * cartItem.getQuantity()))));
            cartItemRepository.save(cartItem);
        }
        shoppingCart.setSubtotal(subtotal(cartItems));
        shoppingCartRepository.save(shoppingCart);
    }


//    SALAH SEMUA INI! HARUS PAKAI CUSTOM QUERY, maybe like ( delete from cart_item where product_id = ? )
    public void deleteCartItem(Product product, User user) {
        if (user.getShoppingCart() == null){
            return;
        }
        cartItemRepository.deleteCartItem(product.getId(), user.getShoppingCart().getId());

        ShoppingCart shoppingCart = shoppingCartRepository.findById(user.getId()).get();
        Set<CartItem> cartItems = shoppingCart.getCartItem();
        shoppingCart.setSubtotal(subtotal(cartItems));
        shoppingCartRepository.save(shoppingCart);

    }
}
