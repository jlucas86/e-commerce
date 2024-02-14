package com.example.exceptions;

public class PaymentMethodsDoNotMatch extends Exception {

    public PaymentMethodsDoNotMatch(String errorMessage) {
        super(errorMessage);
    }

}
