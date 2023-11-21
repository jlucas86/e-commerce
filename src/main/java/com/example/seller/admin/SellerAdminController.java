package com.example.seller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/api/v1/seller")
public class SellerAdminController {

    private final SellerAdminService sellerAdminService;

    @Autowired
    public SellerAdminController(SellerAdminService sellerAdminService) {
        this.sellerAdminService = sellerAdminService;
    }

}
