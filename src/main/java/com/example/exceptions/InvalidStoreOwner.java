package com.example.exceptions;

public class InvalidStoreOwner extends Exception {

    public InvalidStoreOwner(String errorMessage) {
        super(errorMessage);
    }

}
