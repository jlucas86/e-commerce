package com.example.exceptions;

public class StoreDoesNotExist extends Exception {

    public StoreDoesNotExist(String errorMessage) {
        super(errorMessage);
    }

}
