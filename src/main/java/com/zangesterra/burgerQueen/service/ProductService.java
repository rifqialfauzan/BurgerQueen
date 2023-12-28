package com.zangesterra.burgerQueen.service;

import com.zangesterra.burgerQueen.dto.request.ProductRequest;
import com.zangesterra.burgerQueen.dto.response.ProductResponse;
import com.zangesterra.burgerQueen.entity.Product;
import com.zangesterra.burgerQueen.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

//    PR
//    Maybe can use pagination for this service in case of the products getting bigger
    public List<ProductResponse> getAllProducts(){
        List<ProductResponse> responseList = new ArrayList<>();

        List<Product> products = productRepository.findAll();

        for (Product p : products){
            responseList.add(
                    ProductResponse.builder()
                            .id(p.getId())
                            .image(p.getImage())
                            .name(p.getName())
                            .price(p.getPrice())
                            .stock(p.getStock())
                    .build());
        }

        return responseList;
    }

    public ProductResponse getOneProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow();

        return ProductResponse.builder()
                .id(product.getId())
                .image(product.getImage())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();

    }

/*  PR
*   Need validation in case client sent null or empty value
* */
    public ProductResponse addProduct(ProductRequest productRequest){
        Product addProduct = new Product();

        String defaultProductImage = "blabla.jpg";

        if (Objects.isNull(productRequest.getImage())) {
            addProduct.setImage(defaultProductImage);
        } else {
            addProduct.setImage(productRequest.getImage());
        }

        addProduct.setName(productRequest.getName());
        addProduct.setPrice(productRequest.getPrice());
        addProduct.setStock(productRequest.getStock());

        productRepository.save(addProduct);

        return ProductResponse.builder()
                .id(addProduct.getId())
                .image(addProduct.getImage())
                .name(addProduct.getName())
                .price(addProduct.getPrice())
                .stock(addProduct.getStock())
                .build();
    }

    /*
        PR
        Need Validation in case client sent null or empty value
    */
    public ProductResponse updateProduct(ProductRequest request, Long id){
        Product product = productRepository.findById(id).orElseThrow();

        if (Objects.nonNull(request.getImage())){
            product.setImage(request.getImage());
        }

        if (Objects.nonNull(request.getName())){
            product.setName(request.getName());
        }

        if (Objects.nonNull(request.getPrice())){
            product.setPrice(request.getPrice());
        }

        if (Objects.nonNull(request.getStock())){
            product.setStock(request.getStock());
        }

        productRepository.save(product);

        return ProductResponse.builder()
                .id(product.getId())
                .image(product.getImage())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }


}
