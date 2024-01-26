package com.example.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.ProductAlreadyExists;
import com.example.exceptions.ProductNotFound;
import com.example.record.Products;

@Service
public class ProductSevice {

    private final ProductRepository productRepository;

    @Autowired
    public ProductSevice(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getProduct(Integer id) {
        return productRepository.findById(id);
    }

    public List<Product> getProducts(Products p) {

        List<Product> products = new ArrayList<Product>();

        Integer startHold = p.start();
        for (Integer i = 0; i < p.amount();) {
            int j = 0;
            for (String t : p.type()) {
                Optional<Product> product = productRepository.findByTypeAndIdAfter(t, startHold);
                if (!product.isPresent()) {
                    // is empty
                    j++;
                } else {
                    if (!products.contains(product.get())) { // product is not found in list
                        products.add(product.get());
                        i++;
                    }
                }
            }
            if (j > 1) {
                i = p.amount();
            } else {
                if (j == 1)
                    startHold = products.get(products.size() - 1).getId();
                else {
                    int i0 = products.get(products.size() - 1).getId();
                    int i1 = products.get(products.size() - 2).getId();
                    if (i0 > i1)
                        startHold = i1;
                    else
                        startHold = i0;
                }
            }
        }
        return products;
    }

    public void createProduct(Product product) throws ProductAlreadyExists {
        // this is wrong add a way to detect if a product already exits best
        // best bet is to do this by associating a product name with a store
        // if (productRepository.existsById(product.getId())) {
        // throw new ProductAlreadyExists(String.format("product %s already exists",
        // product.getName()));
        // }
        productRepository.save(product);
    }

    public void updateProduct(Product product) throws ProductNotFound {
        if (!productRepository.existsById(product.getId())) {
            throw new ProductNotFound(String.format("product %i not found", product.getId()));
        }
        productRepository.save(product);
    }

    public void deleteProduct(Integer id) throws ProductNotFound {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFound(String.format("product %i not found", id));
        }
        productRepository.deleteById(id);
    }

}
