package com.zangesterra.burgerQueen.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String image;

    @NotBlank(message = "name can not be null")
    private String name;

    @NotNull(message = "price can not be null")
    private Integer price;

    @NotNull(message = "stock can not be null")
    private Integer stock;
}
