package com.zangesterra.burgerQueen.controller;

import com.zangesterra.burgerQueen.entity.Product;
import com.zangesterra.burgerQueen.entity.ShoppingCart;
import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.service.ProductService;
import com.zangesterra.burgerQueen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping({"/", "/index"})
public class MainController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping
    public String home(Model model){
        List<Product> products = productService.allProduct();
        model.addAttribute("title", "Home");
        model.addAttribute("products", products);
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping("login")
    public String login(Model model){
        model.addAttribute("title", "Login");
        return "login";
    }

    @PostMapping("/login")
    public void login(User user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @GetMapping("register")
    public String register(Model model){
        model.addAttribute("title", "Register");
        return "register";
    }

    @GetMapping("profile")
    public String profile(Model model, Principal principal){
        model.addAttribute("title", "Profile");
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("profile", user);
        return "profile";
    }

    @GetMapping("cart")
    public String cart(Model model, Principal principal, Authentication authentication){
        model.addAttribute("title", "Cart");


        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByEmail(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        if (shoppingCart == null) {
            model.addAttribute("nullCart", "There is no item in your cart");
        }
        model.addAttribute("shoppingCart", shoppingCart);

        return "cart";
    }


}
