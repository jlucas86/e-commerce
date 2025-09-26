package com.example.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.exceptions.InvalidStoreOwner;
import com.example.exceptions.ProductNotFound;
import com.example.exceptions.StoreDoesNotExist;
import com.example.exceptions.StoreDoesNotOwnProduct;
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

    // gets a single product based on its id

    public Optional<Product> getProduct(Integer id) {
        // Product p = productRepository.findById(id).get();
        // // if (p != null) {
        // //     System.out.println(p.getStore());
        // //     // p.setStore(storeRepository.findById());
        // // }
        return productRepository.findById(id);
    }

    // // gets a lsit of products of the same type that come after the specified product id

    // public List<Product> getProducts(Products p) {

    //     List<Product> products = new ArrayList<Product>();

    //     Integer startHold = p.start();
    //     for (Integer i = 0; i < p.amount();) {
    //         int j = 0;
    //         for (String t : p.type()) {
    //             Optional<Product> product = productRepository.findByTypeAndIdAfter(t, startHold);
    //             if (!product.isPresent()) {
    //                 // is empty
    //                 j++;
    //             } else {
    //                 if (!products.contains(product.get())) { // product is not found in list
    //                     products.add(product.get());
    //                     i++;
    //                 }
    //             }
    //         }
    //         // their are no more items to get
    //         if (j == p.type().size()) {
    //             i = p.amount();
    //         } else {
    //             // when the lists of products have one ellemnt or only one type was found past
    //             // start value
    //             if (products.size() == 1 || p.type().size() - j == 1)
    //                 startHold = products.get(products.size() - 1).getId();
    //             else { // detemin all
    //                    // int i0 = products.get(products.size() - 1).getId();
    //                    // int i1 = products.get(products.size() - 2).getId();
    //                    // deteming the smallest index of newly added elements in list
    //                 startHold = products.get(products.size() - 1).getId();
    //                 for (Integer x = products.size() - 2; x > products.size() - j - 1; x--) {
    //                     if (products.get(x).getId() < startHold) {
    //                         startHold = products.get(x).getId();
    //                     }
    //                 }
    //                 // if (i0 > i1)
    //                 // startHold = i1;
    //                 // else
    //                 // startHold = i0;
    //                 // }
    //             }
    //         }
    //     }
    //     return products;
    // }


    // get the a pageable list of the products based on the page, size of page, and type of product

    public Page<Product> getProducts(int page, int size, String sortBy, Boolean ascending, List<String> type){
        Sort sort;
        if (ascending)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if(type.size()<= 0){
            return  productRepository.findAll(pageable);
        }
        
        return productRepository.findByTypeIn(type, pageable);

    }

    // get the pageable list of products based on the store name

    /*********************** update late so that it also works with type 
    
     this can be done by add a type list to the param and in the repository add anouther funacion
     that is a modify version of the formate that added AndTypein to the end and a second 
     param to be 

     like above verigy isze of type then  either call function to get by store or get by type and store
    */

    public Page<Product> getStoreProducts(int page, int size, String sortBy, int storeId){
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findByStoreId(storeId, pageable);
    }

    // add new product to the data base after verifying the user owns the store.

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

    // update the products infromateino after verifying the user owns the store and the product exists

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
