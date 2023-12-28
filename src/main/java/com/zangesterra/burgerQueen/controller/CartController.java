package com.zangesterra.burgerQueen.controller;

import com.zangesterra.burgerQueen.entity.CartItem;
import com.zangesterra.burgerQueen.entity.Product;
import com.zangesterra.burgerQueen.entity.ShoppingCart;
import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.repository.ProductRepository;
import com.zangesterra.burgerQueen.repository.UserRepository;
import com.zangesterra.burgerQueen.service.ProductService;
import com.zangesterra.burgerQueen.service.ShoppingCartService;
import com.zangesterra.burgerQueen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class CartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @PostMapping("cart")
    public String addToCart(
            @RequestParam("id") Long id,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            Principal principal,
            HttpServletRequest request
    ){
        if (principal == null){
            return "redirect:/login";
        }

//        Product product = productService.getProduct(id);
        User user = userService.getUserByEmail(principal.getName());

//        shoppingCartService.addToCart(product,quantity,user);

        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("addQuantity")
    private String addQuantity(@RequestParam("id") Long productId,
                               HttpServletRequest request,
                               Principal principal){

//        Product product = productService.getProduct(productId);
        User user = userService.getUserByEmail(principal.getName());

//        shoppingCartService.addQuantity(product,user);
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("subQuantity")
    private String subQuantity(@RequestParam("id") Long productId,
                               HttpServletRequest request,
                               Principal principal){

//        Product product = productService.getProduct(productId);
        User user = userService.getUserByEmail(principal.getName());

//        shoppingCartService.subQuantity(product,user);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("deleteItem/{id}")
    private String deleteCartItem(@PathVariable("id") Long productId,
                                  HttpServletRequest request,
                                  Principal principal){
//        Product product = productService.getProduct(productId);
        User user = userService.getUserByEmail(principal.getName());

//        shoppingCartService.deleteCartItem(product,user);

        return "redirect:" + request.getHeader("Referer");
    }
}
