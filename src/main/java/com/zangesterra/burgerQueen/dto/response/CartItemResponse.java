package com.zangesterra.burgerQueen.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {
    private ProductResponse products;
    private Integer quantity;
    private Double total;
}
