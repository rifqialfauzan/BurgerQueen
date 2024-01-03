package com.zangesterra.burgerQueen.controller;

import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.service.impl.ProductServiceImpl;
import com.zangesterra.burgerQueen.service.ShoppingCartServiceImpl;
import com.zangesterra.burgerQueen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class CartController {

    @Autowired
    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductServiceImpl productServiceImpl;

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
