package com.example.seller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Integer> {

    List<Seller> findByName(String name);

}
