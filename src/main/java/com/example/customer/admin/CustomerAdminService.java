package com.example.customer.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.CustomerRepository;

@Service
public class CustomerAdminService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerAdminService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

}
