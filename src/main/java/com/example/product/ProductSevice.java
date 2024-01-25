package com.example.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Product> getProducts(List<String> type, Integer amount, Integer start) {

        List<Product> products = new ArrayList<Product>();

        Integer startHold = start;
        for (Integer i = 0; i < amount;) {
            int j = 0;
            for (String t : type) {
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
                i = amount;
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

    public void createProduct(Product product) {
        if (!productRepository.existsById(product.getId())) {
            // throw error
        }
        productRepository.save(product);
    }

    public void updateProduct(Product product) {
        if (!productRepository.existsById(product.getId())) {
            // throw error
        }
        productRepository.save(product);
    }

    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            // throw error
        }
        productRepository.deleteById(id);
    }

}
