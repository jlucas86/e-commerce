package com.example.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.exceptions.InvalidStoreOwner;
import com.example.exceptions.ProductAlreadyExists;
import com.example.exceptions.ProductNotFound;
import com.example.exceptions.StoreDoesNotExist;
import com.example.exceptions.StoreDoesNotOwnProduct;
import com.example.record.Pair;
import com.example.record.Products;
import com.example.store.Store;
import com.example.store.StoreRepository;
import com.example.store.StoreService;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;

@Service
public class ProductSevice {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final UserInfoRepository userInfoRepository;
    private final StoreService storeService;

    @Autowired
    public ProductSevice(ProductRepository productRepository, StoreRepository storeRepository,
            UserInfoRepository userInfoRepository, StoreService storeService) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.userInfoRepository = userInfoRepository;
        this.storeService = storeService;
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

    public void createProduct(String username, Integer storeId, Product product) {
        // this is wrong add a way to detect if a product already exits best
        // best bet is to do this by associating a product name with a store
        // if (productRepository.existsById(product.getId())) {
        // throw new ProductAlreadyExists(String.format("product %s already exists",
        // product.getName()));
        // }

        // validate store exists
        try {
            if (!storeRepository.existsById(storeId))
                throw new StoreDoesNotExist(String.format("store %i not found", storeId));
            UserInfo user = userInfoRepository.findByUsername(username).get();
            Store store = storeRepository.findById(storeId).get();
            storeService.validateStoreOwner(user, store);

            product.setStore(store);
            productRepository.save(product);

        } catch (Exception e) {
            System.err.println(e.getMessage() + "++++++++++++++++++++++++++++++++++++++++++ urg");
        }

        productRepository.save(product);
    }

    public void updateProduct(String username, Integer storeId, Product product) {

        try {
            verifiedProduct(product, storeId, username);
            productRepository.save(product);
        } catch (Exception e) {
            System.err.println(e.getMessage() + "++++++++++++++++++++++++++++++++++++++++++ urg");
        }
    }

    public void deleteProduct(String username, Integer storeId, Product product) {
        try {
            verifiedProduct(product, storeId, username);
            productRepository.delete(product);
        } catch (Exception e) {
            System.err.println(e.getMessage() + "++++++++++++++++++++++++++++++++++++++++++ urg");
        }

    }

    public void verifiedProduct(Product product, Integer storeId, String username)
            throws ProductNotFound, StoreDoesNotExist, StoreDoesNotOwnProduct, InvalidStoreOwner {
        if (!productRepository.existsById(product.getId())) {
            throw new ProductNotFound(String.format("product %i not found", product.getId()));
        }
        UserInfo user = userInfoRepository.findByUsername(username).get();
        if (!storeRepository.existsById(storeId))
            throw new StoreDoesNotExist(String.format("store %i not found", storeId));
        Store store = storeRepository.findById(storeId).get();
        if (product.getStore().getId() != store.getId()) {
            throw new StoreDoesNotOwnProduct(
                    String.format("store %i does not own product %i", storeId, product.getId()));
        }
        if (user.getId() != store.getUser().getId()) {
            throw new InvalidStoreOwner(
                    String.format("Username %s does not own store %s", user.getUsername(), store.getName()));
        }

    }

}
