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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    private AuthenticationManager authenticationManager;





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


        Product product = productService.getProduct(id);
        User user = userService.getUserByEmail(principal.getName());

        shoppingCartService.addToCart(product,quantity,user);

        return "redirect:" + request.getHeader("Referer");

    }

}
