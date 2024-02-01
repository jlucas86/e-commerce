package com.example.exceptions;

public class UserDoesNotOwnPaymentMethod extends Exception {

    public UserDoesNotOwnPaymentMethod(String errorMessage) {
        super(errorMessage);
    }
}
