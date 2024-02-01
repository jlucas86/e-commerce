package com.example.exceptions;

public class PaymentMethodDoesNotExist extends Exception {

    public PaymentMethodDoesNotExist(String errorMessage) {
        super(errorMessage);
    }
}
