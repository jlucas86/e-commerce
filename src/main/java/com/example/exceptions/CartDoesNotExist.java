package com.example.exceptions;

public class CartDoesNotExist extends Exception {

    public CartDoesNotExist(String errorMessage) {
        super(errorMessage);
    }

}
