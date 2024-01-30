package com.example.exceptions;

public class StoreDoesNotOwnProduct extends Exception {

    public StoreDoesNotOwnProduct(String errorMessage) {
        super(errorMessage);
    }

}
