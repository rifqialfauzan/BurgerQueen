package com.zangesterra.burgerQueen.controller;

import com.zangesterra.burgerQueen.Validator.UserValidator;
import com.zangesterra.burgerQueen.entity.Product;
import com.zangesterra.burgerQueen.entity.ShoppingCart;
import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.service.ProductService;
import com.zangesterra.burgerQueen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

//@Controller
//@RequestMapping({"/", "/index"})
public class MainController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;


    @GetMapping
    public String home(Model model){
//        List<Product> products = productService.allProduct();
        model.addAttribute("title", "Home");
//        model.addAttribute("products", products);
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

    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = "Email or Password is not correct";
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @GetMapping("register")
    public String register(Model model){
        model.addAttribute("title", "Register");
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("register")
    public String register( User user, BindingResult bindingResult){
        userValidator.validate(user,bindingResult);

        if (bindingResult.hasErrors()){
            return "register";
        }
//        userService.addUser(user);
        return "redirect:/login";
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

        if (shoppingCart == null ) {
            model.addAttribute("nullCart", "There is no item in your cart");
        }

        if (shoppingCart.getCartItem().size() == 0){
            model.addAttribute("noItem", "There is no item in your cart");
        }
        model.addAttribute("shoppingCart", shoppingCart);

        return "cart";
    }

    @GetMapping("profileUpdate/{id}")
    public String profileUpdateForm(Model model,@PathVariable("id") Long id){
//        User user = userService.getUser(id);
//        model.addAttribute("user", user);
        model.addAttribute("title", "Update profile");


        return "profile-update";
    }

}
