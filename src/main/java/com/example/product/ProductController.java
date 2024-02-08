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

    private final ProductService productSevice;

    @Autowired
    public ProductController(ProductService productSevice) {
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
    @PostMapping("/createProduct/{username}/{storeId}")
    public void createProduct(@PathVariable("username") String username, @PathVariable("storeId") Integer storeId,
            @RequestBody Product product) {

        try {
            productSevice.createProduct(username, storeId, product);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_SELLER')")
    @PostMapping("/updateProduct/{username}/{storeId}")
    public void updateProduct(@PathVariable("username") String username, @PathVariable("storeId") Integer storeId,
            @RequestBody Product product) {

        try {
            productSevice.updateProduct(username, storeId, product);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_SELLER')")
    @DeleteMapping("/deleteProduct/{username}/{storeId}")
    public void deleteProduct(@PathVariable("username") String username, @PathVariable("storeId") Integer storeId,
            @RequestBody Product product) {

        try {
            productSevice.deleteProduct(username, storeId, product);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
