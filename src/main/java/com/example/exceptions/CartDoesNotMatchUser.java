package com.example.exceptions;

public class CartDoesNotMatchUser extends Exception {

    public CartDoesNotMatchUser(String errorMessage) {
        super(errorMessage);
    }

}
