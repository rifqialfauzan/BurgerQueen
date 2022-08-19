package com.zangesterra.burgerQueen.service;

import com.zangesterra.burgerQueen.entity.Product;
import com.zangesterra.burgerQueen.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> allProduct(){
        return productRepository.findAll();
    }

    public Product getProduct(Long id){
        return productRepository.findById(id).get();
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }


}
