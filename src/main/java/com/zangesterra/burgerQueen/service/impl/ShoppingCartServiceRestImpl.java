package com.zangesterra.burgerQueen.service.impl;

import com.zangesterra.burgerQueen.dto.response.CartItemResponse;
import com.zangesterra.burgerQueen.dto.response.ProductResponse;
import com.zangesterra.burgerQueen.dto.response.ShoppingCartResponse;
import com.zangesterra.burgerQueen.entity.CartItem;
import com.zangesterra.burgerQueen.entity.Product;
import com.zangesterra.burgerQueen.entity.ShoppingCart;
import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.repository.CartItemRepository;
import com.zangesterra.burgerQueen.repository.ProductRepository;
import com.zangesterra.burgerQueen.repository.ShoppingCartRepository;
import com.zangesterra.burgerQueen.repository.UserRepository;
import com.zangesterra.burgerQueen.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//    -TODO-
/*
    * Method that add or sub quantity product in cart still doesn't check the product's stock. So that should be concerned
    * Need to learn to create custom error and error handling
* */

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceRestImpl implements ShoppingCartService {

    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    public ShoppingCartResponse getAllUserCartItems(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();

//        I don't know why but this check is a MUST even though I set user cart to "new ShoppingCart()" in register
        if (Objects.isNull(user.getShoppingCart())){
            user.setShoppingCart(new ShoppingCart());
        }

//        Get user's cart
        ShoppingCart userShoppingCart = user.getShoppingCart();
        Set<CartItem> cartItems = userShoppingCart.getCartItem();

        Set<CartItemResponse> cartItemResponses = new HashSet<>();
        if (Objects.nonNull(cartItems)){

            for (CartItem c : cartItems){
//                Map to response
                ProductResponse productResponse = mapProductToProductResponse(c.getProduct());
                CartItemResponse cartItemResponse = mapCartItemToCartItemResponse(c, productResponse);
                cartItemResponses.add(cartItemResponse);
            }
        }

        return mapShoppingCartToShoppingCartResponse(cartItemResponses, userShoppingCart);
    }

    @Override
    public ShoppingCartResponse addToShoppingCart(Long productId, int amount, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

//        I don't know why but this check is a MUST even though I set user cart to "new ShoppingCart()" in register
        if (Objects.isNull(user.getShoppingCart())){
            user.setShoppingCart(new ShoppingCart());
        }

//        if (amount > product.getStock()){
////            error stock not enough
//        }

        ShoppingCart userShoppingCart = user.getShoppingCart();
        Set<CartItem> cartItems = userShoppingCart.getCartItem();
        CartItem ci = new CartItem();

        Set<CartItemResponse> cartItemResponses = new HashSet<>();

        if (Objects.isNull(cartItems)){
//            Set new cartItem
            ci.setProduct(product);
            ci.setShoppingCart(userShoppingCart);
            ci.setQuantity(amount);
            ci.setTotal(Double.valueOf(decimalFormat.format(calculateTotalPricePerProduct(product.getPrice(), amount))));
            cartItemRepository.save(ci); // save cartItem to db

//            Add cartItem to User cartItems
            cartItems = new HashSet<>();
            cartItems.add(ci);
        }else {
//            Check if the product already in cart
            for (CartItem cartItem : cartItems){
//                if it is than add the quantity
                if (Objects.equals(product, cartItem.getProduct())){
//                    if (cartItem.getQuantity() + amount > cartItem.getProduct().getStock()){
//                        // error stock not enough
//                    }
                    cartItem.setQuantity(cartItem.getQuantity() + amount);
                    cartItem.setTotal(Double.valueOf(decimalFormat.format(calculateTotalPricePerProduct(cartItem.getProduct().getPrice(), cartItem.getQuantity()))));
                    cartItemRepository.save(cartItem); // save changes to db

 //                   update the user cart by adding product they want to their cart
                    userShoppingCart.setUser(user);
                    userShoppingCart.setCartItem(cartItems);
                    userShoppingCart.setSubtotal(calculateTotalPriceOfUserCart(cartItems));
                    shoppingCartRepository.save(userShoppingCart); // save it to db

//                    Map to Response
                    ProductResponse productResponse = mapProductToProductResponse(product);
                    CartItemResponse cartItemResponse = mapCartItemToCartItemResponse(cartItem, productResponse);
                    cartItemResponses.add(cartItemResponse);

                    return mapShoppingCartToShoppingCartResponse(cartItemResponses, userShoppingCart);
                }
            }

//            if the product not in the cart than add the product to the cart
            ci.setProduct(product);
            ci.setShoppingCart(userShoppingCart);
            ci.setQuantity(amount);
            ci.setTotal(calculateTotalPricePerProduct(product.getPrice(), amount));

            cartItems.add(ci);
            cartItemRepository.save(ci); // save cartItem to db
        }

//            update the user cart by adding product they want to their cart
        userShoppingCart.setUser(user);
        userShoppingCart.setCartItem(cartItems);
        userShoppingCart.setSubtotal(calculateTotalPriceOfUserCart(cartItems));
        shoppingCartRepository.save(userShoppingCart); // save it to db

        ProductResponse productResponse = mapProductToProductResponse(product);
        CartItemResponse cartItemResponse = mapCartItemToCartItemResponse(ci, productResponse);
        cartItemResponses.add(cartItemResponse);


        return mapShoppingCartToShoppingCartResponse(cartItemResponses, userShoppingCart);
    }




    @Override
    public ShoppingCartResponse addCartItemQuantity(Long productId, int amount, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        ShoppingCart userShoppingCart = user.getShoppingCart();
        Set<CartItem> cartItems = userShoppingCart.getCartItem();

        Set<CartItemResponse> cartItemResponses = new HashSet<>();

        for (CartItem cartItem : cartItems){
            if (Objects.equals(cartItem.getProduct(), product)){
                cartItem.setQuantity(cartItem.getQuantity() + amount);
                cartItem.setTotal(Double.valueOf(decimalFormat.format(calculateTotalPricePerProduct(cartItem.getProduct().getPrice(), cartItem.getQuantity()))));
                cartItemRepository.save(cartItem);

                userShoppingCart.setUser(user);
                userShoppingCart.setCartItem(cartItems);
                userShoppingCart.setSubtotal(calculateTotalPriceOfUserCart(cartItems));
                shoppingCartRepository.save(userShoppingCart); // save it to db

//                    Map to Response
                ProductResponse productResponse = mapProductToProductResponse(product);
                CartItemResponse cartItemResponse = mapCartItemToCartItemResponse(cartItem, productResponse);
                cartItemResponses.add(cartItemResponse);
            }
        }

        return mapShoppingCartToShoppingCartResponse(cartItemResponses, userShoppingCart);
    }

    @Override
    public ShoppingCartResponse subCartItemQuantity(Long productId, int amount, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        ShoppingCart userShoppingCart = user.getShoppingCart();
        Set<CartItem> cartItems = userShoppingCart.getCartItem();

        Set<CartItemResponse> cartItemResponses = new HashSet<>();

        for (CartItem cartItem : cartItems){
            if (Objects.equals(cartItem.getProduct(), product)){
                if (cartItem.getQuantity() > 1){
                    cartItem.setQuantity(cartItem.getQuantity() - amount);
                    cartItem.setTotal(Double.valueOf(decimalFormat.format(calculateTotalPricePerProduct(cartItem.getProduct().getPrice(), cartItem.getQuantity()))));
                    cartItemRepository.save(cartItem);
                }

                userShoppingCart.setUser(user);
                userShoppingCart.setCartItem(cartItems);
                userShoppingCart.setSubtotal(calculateTotalPriceOfUserCart(cartItems));
                shoppingCartRepository.save(userShoppingCart); // save it to db

//                    Map to Response
                ProductResponse productResponse = mapProductToProductResponse(product);
                CartItemResponse cartItemResponse = mapCartItemToCartItemResponse(cartItem, productResponse);
                cartItemResponses.add(cartItemResponse);
            }
        }

        return mapShoppingCartToShoppingCartResponse(cartItemResponses, userShoppingCart);
    }

//      -TODO-
//    Somehow it works But sometimes it throws concurrent error because I set shoppingCart to Null before deleted it
//    The problem is I think around foreign key in db
    @Override
    public ShoppingCartResponse removeCartItemFromShoppingCart(Long productId, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        ShoppingCart userShoppingCart = user.getShoppingCart();
        Set<CartItem> cartItems = userShoppingCart.getCartItem();

        Set<CartItemResponse> cartItemResponses = new HashSet<>();
        for (CartItem cartItem : cartItems){
            if (Objects.equals(cartItem.getProduct(), product)){
                cartItem.setShoppingCart(null);
                cartItem.setTotal(0.0);
                cartItemRepository.deleteById(cartItem.getId());

                userShoppingCart.setUser(user);
                userShoppingCart.setCartItem(cartItems);
                userShoppingCart.setSubtotal(calculateTotalPriceOfUserCart(cartItems));
                shoppingCartRepository.save(userShoppingCart); // save it to db

//                    Map to Response
                ProductResponse productResponse = mapProductToProductResponse(product);
                CartItemResponse cartItemResponse = mapCartItemToCartItemResponse(cartItem, productResponse);
                cartItemResponses.add(cartItemResponse);
            }
        }

        return mapShoppingCartToShoppingCartResponse(cartItemResponses, userShoppingCart);
    }

    public ProductResponse mapProductToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .image(product.getImage())
                .price(product.getPrice())
                .build();
    }

    public CartItemResponse mapCartItemToCartItemResponse(CartItem cartItem, ProductResponse productResponse){
        return CartItemResponse.builder()
                .products(productResponse)
                .quantity(cartItem.getQuantity())
                .total(cartItem.getTotal())
                .build();
    }

    public ShoppingCartResponse mapShoppingCartToShoppingCartResponse(Set<CartItemResponse> cartItemResponses, ShoppingCart shoppingCart){
        return ShoppingCartResponse.builder()
                .id(shoppingCart.getId())
                .cartItems(cartItemResponses)
                .totalPrice(shoppingCart.getSubtotal())
                .build();
    }

    private Double calculateTotalPricePerProduct(Integer price, int amount) {
        return (double) (price * amount);
    }

    private Double calculateTotalPriceOfUserCart(Set<CartItem> cartItems) {
        Double total = 0.0;
        for (CartItem ci : cartItems){
            total+= ci.getTotal();
        }
        return total;
    }
}
