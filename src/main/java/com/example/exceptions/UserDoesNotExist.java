package com.example.exceptions;

public class UserDoesNotExist extends Exception {

    public UserDoesNotExist(String errorMessage) {
        super(errorMessage);
    }

}
