package com.example.demo.utils;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String msg) {
        super(msg);
    }
}
