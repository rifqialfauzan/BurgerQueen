package com.zangesterra.burgerQueen.controller;

import com.zangesterra.burgerQueen.dto.request.ProductRequest;
import com.zangesterra.burgerQueen.dto.request.UpdateProductRequest;
import com.zangesterra.burgerQueen.dto.response.ProductResponse;
import com.zangesterra.burgerQueen.dto.response.WebResponse;
import com.zangesterra.burgerQueen.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @GetMapping
    public ResponseEntity<WebResponse<List<ProductResponse>>> getAllProducts(){
        List<ProductResponse> products = productServiceImpl.getAllProducts();
        return new ResponseEntity<>(WebResponse.<List<ProductResponse>>builder().data(products).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<ProductResponse>> getOneProduct(@PathVariable Long id){
        ProductResponse product = productServiceImpl.getOneProduct(id);
        return new ResponseEntity<>(WebResponse.<ProductResponse>builder().data(product).build(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<WebResponse<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest){
        ProductResponse product = productServiceImpl.addProduct(productRequest);
        return new ResponseEntity<>(WebResponse.<ProductResponse>builder().data(product).build(), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<WebResponse<ProductResponse>> updateProduct(@RequestBody UpdateProductRequest productRequest, @RequestParam Long id){
        ProductResponse product = productServiceImpl.updateProduct(productRequest, id);
        return new ResponseEntity<>(WebResponse.<ProductResponse>builder().data(product).build(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<WebResponse<String>> deleteProduct(@PathVariable Long id){
        productServiceImpl.deleteProduct(id);
        return new ResponseEntity<>(WebResponse.<String>builder().data("Product deleted successfully").build(), HttpStatus.OK);
    }


}
