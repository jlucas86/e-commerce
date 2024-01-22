package com.example.paymentMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService {

    private final PaymantMethodRepository paymantMethodRepository;

    @Autowired
    public PaymentMethodService(PaymantMethodRepository paymantMethodRepository) {
        this.paymantMethodRepository = paymantMethodRepository;
    }

}
