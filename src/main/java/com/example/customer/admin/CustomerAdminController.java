package com.example.customer.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/api/v1/customer")
public class CustomerAdminController {

    private final CustomerAdminService customerAdminService;

    @Autowired
    public CustomerAdminController(CustomerAdminService customerAdminService) {
        this.customerAdminService = customerAdminService;
    }

}
