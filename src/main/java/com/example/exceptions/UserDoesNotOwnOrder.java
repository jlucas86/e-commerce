package com.example.exceptions;

public class UserDoesNotOwnOrder extends Exception {

    public UserDoesNotOwnOrder(String errorMessage) {
        super(errorMessage);
    }

}
