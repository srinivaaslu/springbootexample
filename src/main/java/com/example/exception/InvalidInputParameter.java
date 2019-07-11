package com.example.exception;

public class InvalidInputParameter extends RuntimeException {

    public InvalidInputParameter(String message) {
        super(message);
    }
}
