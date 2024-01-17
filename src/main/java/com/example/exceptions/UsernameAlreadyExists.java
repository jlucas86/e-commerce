package com.example.exceptions;

public class UsernameAlreadyExists extends Exception {

    public UsernameAlreadyExists(String errorMessage) {
        super(errorMessage);
    }

}
