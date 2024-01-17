package com.example.exceptions;

public class InvalidPassword extends Exception {

    public InvalidPassword(String errorMessage) {
        super(errorMessage);
    }

}
