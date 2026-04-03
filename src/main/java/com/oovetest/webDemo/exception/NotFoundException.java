package com.oovetest.webDemo.exception;

public class NotFoundException extends AppException {
    public NotFoundException(String resource) {
        super("NOT_FOUND", resource);
    }
}
