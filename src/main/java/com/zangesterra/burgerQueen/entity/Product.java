package com.zangesterra.burgerQueen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String name;
    private Integer price;
    private Integer stock;

    public Product(String image, String name, Integer price, Integer stock) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
