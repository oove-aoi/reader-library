package com.oovetest.webDemo.exception.dto;

import lombok.Data;

@Data
public class ErrorDTO {
    private String errorCode;
    private String message;

    public ErrorDTO(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
