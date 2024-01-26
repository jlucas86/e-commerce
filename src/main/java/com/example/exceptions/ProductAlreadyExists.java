package com.example.exceptions;

public class ProductAlreadyExists extends Exception {

    public ProductAlreadyExists(String errorMessage) {
        super(errorMessage);
    }

}
