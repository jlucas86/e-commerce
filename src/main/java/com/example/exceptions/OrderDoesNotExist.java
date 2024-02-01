package com.example.exceptions;

public class OrderDoesNotExist extends Exception {

    public OrderDoesNotExist(String errorMessage) {
        super(errorMessage);
    }

}
