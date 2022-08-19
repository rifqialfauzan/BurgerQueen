package com.zangesterra.burgerQueen.controller;

import com.zangesterra.burgerQueen.entity.Product;
import com.zangesterra.burgerQueen.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("add")
    public void addProduct(@RequestBody Product product){
        productService.addProduct(product);
    }
}
