package com.zangesterra.burgerQueen.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    private String image;

    private String name;

    private Integer price;

    private Integer stock;
}
