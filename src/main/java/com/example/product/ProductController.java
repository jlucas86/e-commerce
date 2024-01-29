package com.example.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.record.Products;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductSevice productSevice;

    @Autowired
    public ProductController(ProductSevice productSevice) {
        this.productSevice = productSevice;
    }

    @GetMapping("/getProduct/{id}")
    public Optional<Product> getProduct(@PathVariable("id") Integer id) {
        try {
            return productSevice.getProduct(id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/getProducts")
    public List<Product> getProducts(@RequestBody Products p) {
        try {
            return productSevice.getProducts(p);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_SELLER')")
    @PostMapping("/createProduct/{username}")
    public void createProduct(@RequestBody Product product, @PathVariable("username") String username) {
        try {
            productSevice.createProduct(product);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_SELLER')")
    @PostMapping("/updateProduct")
    public void updateProduct(@RequestBody Product product) {
        try {
            productSevice.updateProduct(product);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_SELLER')")
    @DeleteMapping("/deleteProduct")
    public void deleteProduct(@PathVariable("id") Integer id) {
        try {
            productSevice.deleteProduct(id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
