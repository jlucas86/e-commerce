package com.example.product;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Page<Product> findAllByType(String type, Pageable pageable);
    List<Product> findAllByStoreId(Integer store_id);

    Optional<Product> findByTypeAndIdAfter(String type, Integer start);
}
