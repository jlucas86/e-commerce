// package com.example.product.admin;

// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.product.Product;
// import com.example.product.ProductRepository;

// @Service
// public class ProductAdminService {

// private final ProductRepository productRepository;

// @Autowired
// public ProductAdminService(ProductRepository productRepository) {
// this.productRepository = productRepository;
// }

// public Optional<Product> getProduct(Integer id) {
// return productRepository.findById(id);
// }

// public void addProduct(Product product) {
// productRepository.save(product);
// }

// public void updateProduct(Integer id, Product product) {
// if (productRepository.existsById(id)) {
// productRepository.save(product);
// }
// }

// public void deleteProduct(Integer id) {
// if (productRepository.existsById(id)) {
// productRepository.deleteById(id);
// }
// }
// }
