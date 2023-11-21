package com.example.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSevice {

    private final ProductRepository productRepository;

    @Autowired
    public ProductSevice(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

}
