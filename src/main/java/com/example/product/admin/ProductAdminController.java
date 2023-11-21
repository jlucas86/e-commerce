package com.example.product.admin;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.Product;
import com.example.product.ProductSevice;
import com.example.seller.Seller;

@RestController
@RequestMapping("/admin/api/v1/product")
public class ProductAdminController {

    private final ProductAdminService productAdminService;

    @Autowired
    public ProductAdminController(ProductAdminService productAdminService) {
        this.productAdminService = productAdminService;
    }

    @GetMapping("/getProduct/{id}")
    @PreAuthorize("hasAuthority('product:read')")
    public Optional<Product> getProduct(@PathVariable("id") Integer id) {
        return productAdminService.getProduct(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addProduct(@RequestBody Product product) {
        productAdminService.addProduct(product);
    }

    @PutMapping(path = ("{id}"))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updatePorduct(@PathVariable("id") Integer id, @RequestBody Product product) {
        productAdminService.updateProduct(id, product);
    }

    @DeleteMapping(path = ("{id}"))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProduct(@PathVariable("id") Integer id) {
        productAdminService.deleteProduct(id);
    }

}
