package com.example.product;

import java.awt.SystemTray;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.exceptions.InvalidStoreOwner;
import com.example.exceptions.ProductAlreadyExists;
import com.example.exceptions.ProductNotFound;
import com.example.exceptions.StoreDoesNotExist;
import com.example.exceptions.StoreDoesNotOwnProduct;
import com.example.record.Pair;
import com.example.record.Products;
import com.example.role.Role;
import com.example.store.Store;
import com.example.store.StoreRepository;
import com.example.store.StoreService;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final UserInfoRepository userInfoRepository;
    private final StoreService storeService;

    @Autowired
    public ProductService(ProductRepository productRepository, StoreRepository storeRepository,
            UserInfoRepository userInfoRepository, StoreService storeService) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.userInfoRepository = userInfoRepository;
        this.storeService = storeService;
    }

    public Optional<Product> getProduct(Integer id) {
        // Product p = productRepository.findById(id).get();
        // // if (p != null) {
        // //     System.out.println(p.getStore());
        // //     // p.setStore(storeRepository.findById());
        // // }
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
            // their are no more items to get
            if (j == p.type().size()) {
                i = p.amount();
            } else {
                // when the lists of products have one ellemnt or only one type was found past
                // start value
                if (products.size() == 1 || p.type().size() - j == 1)
                    startHold = products.get(products.size() - 1).getId();
                else { // detemin all
                       // int i0 = products.get(products.size() - 1).getId();
                       // int i1 = products.get(products.size() - 2).getId();
                       // deteming the smallest index of newly added elements in list
                    startHold = products.get(products.size() - 1).getId();
                    for (Integer x = products.size() - 2; x > products.size() - j - 1; x--) {
                        if (products.get(x).getId() < startHold) {
                            startHold = products.get(x).getId();
                        }
                    }
                    // if (i0 > i1)
                    // startHold = i1;
                    // else
                    // startHold = i0;
                    // }
                }
            }
        }
        return products;
    }

    public void createProduct(String username, Integer storeId, Product product)
            throws StoreDoesNotExist, InvalidStoreOwner, StoreDoesNotExist {
        // this is wrong add a way to detect if a product already exits best
        // best bet is to do this by associating a product name with a store
        // if (productRepository.existsById(product.getId())) {
        // throw new ProductAlreadyExists(String.format("product %s already exists",
        // product.getName()));
        // }

        // validate store exists

        if (!storeRepository.existsById(storeId))
            throw new StoreDoesNotExist(String.format("Store %d not found", storeId));
        UserInfo user = userInfoRepository.findByUsername(username).get();
        Store store = storeRepository.findById(storeId).get();
        if (user.getId() != store.getUser().getId()) {
            throw new InvalidStoreOwner(
                    String.format("Username %s does not own store %d", user.getUsername(), store.getId()));
        }
    
        product.setStore(store);
        productRepository.save(product);

        // productRepository.save(product);
    }

    public void updateProduct(String username, Integer storeId, Product product)
            throws ProductNotFound, StoreDoesNotExist, StoreDoesNotOwnProduct, InvalidStoreOwner {

        verifiedProduct(product, storeId, username);
        Product p = productRepository.findById(product.getId()).get();
        p.setPrice(product.getPrice());
        p.setDescription(product.getDescription());
        p.setType(product.getType());
        p.setName(product.getName());
        productRepository.save(p);

    }

    public void deleteProduct(String username, Integer storeId, Integer productId)
            throws ProductNotFound, StoreDoesNotExist, StoreDoesNotOwnProduct, InvalidStoreOwner {

        Optional<Product> p = productRepository.findById(productId);
        if(p.isEmpty()){
            // thorw error
            throw new  ProductNotFound(String.format("Product %d not found", productId));
        }

        Product product = p.get();
        System.out.println(product);
        verifiedProduct(product, storeId, username);
        productRepository.delete(product);

    }

    public void verifiedProduct(Product product, Integer storeId, String username)
            throws ProductNotFound, StoreDoesNotExist, StoreDoesNotOwnProduct, InvalidStoreOwner {
        if (!productRepository.existsById(product.getId())) {
            throw new ProductNotFound(String.format("Product %d not found", product.getId()));
        }
        UserInfo user = userInfoRepository.findByUsername(username).get();
        if (!storeRepository.existsById(storeId))
            throw new StoreDoesNotExist(String.format("Store %d not found", storeId));
        Store store = storeRepository.findById(storeId).get();
        if (product.getStore().getId() != store.getId()) {
            throw new StoreDoesNotOwnProduct(
                    String.format("Store %d does not own product %d", storeId, product.getId()));
        }
        if (user.getId() != store.getUser().getId()) {
            throw new InvalidStoreOwner(
                    String.format("Username %s does not own store %s", user.getUsername(), store.getName()));
        }

    }

}
