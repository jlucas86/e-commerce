package com.example.exceptions;

public class ProductNotFound extends Exception {

    public ProductNotFound(String errorMessage) {
        super(errorMessage);
    }

}
