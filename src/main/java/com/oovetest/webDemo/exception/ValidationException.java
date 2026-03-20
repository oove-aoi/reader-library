package com.oovetest.webDemo.exception;

public class ValidationException extends AppException {
    public ValidationException(String message) {
        super("VALIDATION_ERROR", message);
    }
    
}
