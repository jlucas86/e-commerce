package com.example.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductSevice productSevice;

    @Autowired
    public ProductController(ProductSevice productSevice) {
        this.productSevice = productSevice;
    }

    // @GetMapping("/getProduct/{id}")
    // public List<Seller> getProduct(@PathVariable("id") Integer id) {
    // return ProductSevice.getSellers();
    // }

}
