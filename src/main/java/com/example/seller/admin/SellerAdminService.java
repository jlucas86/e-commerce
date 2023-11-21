package com.example.seller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.seller.SellerRepository;

@Service
public class SellerAdminService {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerAdminService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

}
