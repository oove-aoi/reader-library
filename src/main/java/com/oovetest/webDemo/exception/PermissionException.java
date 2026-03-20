package com.oovetest.webDemo.exception;

public class PermissionException extends AppException {
    public PermissionException(String message) {
        super("PERMISSION_DENIED", message);
    }
}
