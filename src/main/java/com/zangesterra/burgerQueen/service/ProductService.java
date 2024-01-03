package com.zangesterra.burgerQueen.service;

import com.zangesterra.burgerQueen.dto.request.ProductRequest;
import com.zangesterra.burgerQueen.dto.request.UpdateProductRequest;
import com.zangesterra.burgerQueen.dto.response.ProductResponse;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ProductService {

    List<ProductResponse> getAllProducts();
    ProductResponse getOneProduct(Long id);
    ProductResponse addProduct(@Valid ProductRequest productRequest);
    ProductResponse updateProduct(UpdateProductRequest request, Long id);
    void deleteProduct(Long id);
}
