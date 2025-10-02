package com.example.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Page<Product> findAllByType(String type, Pageable pageable);

    // List<Store> findAllByUserId(Integer user_id);

    Page<Product> findByStoreId(Integer store_id, Pageable p);

    Page<Product> findByStoreIdAndTypeIn(Integer store_id, List<String> type, Pageable p);  

    Page<Product> findByTypeIn(List<String> type, Pageable p);
}
