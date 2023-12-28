package com.zangesterra.burgerQueen.controller;

import com.zangesterra.burgerQueen.dto.request.ProductRequest;
import com.zangesterra.burgerQueen.dto.response.ProductResponse;
import com.zangesterra.burgerQueen.dto.response.WebResponse;
import com.zangesterra.burgerQueen.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<WebResponse<List<ProductResponse>>> getAllProducts(){
        List<ProductResponse> products = productService.getAllProducts();
        return new ResponseEntity<>(WebResponse.<List<ProductResponse>>builder().data(products).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<ProductResponse>> getOneProduct(@PathVariable Long id){
        ProductResponse product = productService.getOneProduct(id);
        return new ResponseEntity<>(WebResponse.<ProductResponse>builder().data(product).build(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<WebResponse<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest){
        ProductResponse product = productService.addProduct(productRequest);
        return new ResponseEntity<>(WebResponse.<ProductResponse>builder().data(product).build(), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<WebResponse<ProductResponse>> updateProduct(@RequestBody ProductRequest productRequest, @RequestParam Long id){
        ProductResponse product = productService.updateProduct(productRequest, id);
        return new ResponseEntity<>(WebResponse.<ProductResponse>builder().data(product).build(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<WebResponse<String>> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(WebResponse.<String>builder().data("Product deleted successfully").build(), HttpStatus.OK);
    }


}
