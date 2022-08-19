package com.zangesterra.burgerQueen.configuration;

import com.zangesterra.burgerQueen.entity.Product;
import com.zangesterra.burgerQueen.repository.ProductRepository;

public class InsertProducts {

    private ProductRepository productRepository;

    public void insertProducts(){

        Product product = new Product();
        product.setPrice(23000);
        product.setStock(10);
        product.setName("Italian cheese burger");
        product.setImage("Italian cheese burger.jpg");
        productRepository.save(product);

        Product product2 = new Product();
        product2.setPrice(23000);
        product2.setStock(10);
        product2.setName("Buttermilk chicken burger");
        product2.setImage("Buttermilk chicken burger.jpg");
        productRepository.save(product2);

        Product product3 = new Product();
        product3.setPrice(25000);
        product3.setStock(10);
        product3.setName("Chorizo burger");
        product3.setImage("Chorizo burger.jpg");
        productRepository.save(product3);

        Product product4 = new Product();
        product4.setPrice(30000);
        product4.setStock(10);
        product4.setName("Cicken katsu burger");
        product4.setImage("Cicken katsu burger.jpg");
        productRepository.save(product4);

        Product product5 = new Product();
        product5.setPrice(28000);
        product5.setStock(10);
        product5.setName("Crispy quinoa burger");
        product5.setImage("Crispy quinoa burger.jpg");
        productRepository.save(product5);

        Product product6 = new Product();
        product6.setPrice(25000);
        product6.setStock(10);
        product6.setName("Italian cheese burger");
        product6.setImage("Italian cheese burger.jpg");
        productRepository.save(product6);

        Product product7 = new Product();
        product7.setPrice(28000);
        product7.setStock(10);
        product7.setName("Ground beef burger");
        product7.setImage("Ground beef burger.jpg");
        productRepository.save(product7);

        Product product8 = new Product();
        product8.setPrice(25000);
        product8.setStock(10);
        product8.setName("Italian cheese burger");
        product8.setImage("Italian cheese burger.jpg");
        productRepository.save(product8);

        Product product9 = new Product();
        product9.setPrice(30000);
        product9.setStock(10);
        product9.setName("Honey bacon burger");
        product9.setImage("Honey bacon burger.jpg");
        productRepository.save(product9);

        Product product10 = new Product();
        product10.setPrice(28000);
        product10.setStock(10);
        product10.setName("Honey mustard chicken burger");
        product10.setImage("Honey mustard chicken burger.jpg");
        productRepository.save(product10);

        Product product11 = new Product();
        product11.setPrice(30000);
        product11.setStock(10);
        product11.setName("Korean beef sloppy joes burger");
        product11.setImage("Korean beef sloppy joes burger.jpg");
        productRepository.save(product11);

        Product product12 = new Product();
        product12.setPrice(25000);
        product12.setStock(10);
        product12.setName("Tempeh black bean burger");
        product12.setImage("Tempeh black bean burger.jpg");
        productRepository.save(product12);

        Product product13 = new Product();
        product13.setPrice(30000);
        product13.setStock(10);
        product13.setName("Mushroom emmental beef burger");
        product13.setImage("Mushroom emmental beef burger.jpg");
        productRepository.save(product13);

        Product product14 = new Product();
        product14.setPrice(28000);
        product14.setStock(10);
        product14.setName("Ultimate double cheese burger");
        product14.setImage("Ultimate double cheese burger.jpg");
        productRepository.save(product14);



    }
}
