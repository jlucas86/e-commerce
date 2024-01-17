package com.example.exceptions;

public class EmailAlreadyExists extends Exception {

    public EmailAlreadyExists(String errorMessage) {
        super(errorMessage);
    }

}
