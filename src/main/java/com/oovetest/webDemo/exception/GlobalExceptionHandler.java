package com.oovetest.webDemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oovetest.webDemo.exception.dto.ErrorDTO;
import com.oovetest.webDemo.exception.NotFoundException;
import com.oovetest.webDemo.exception.PermissionException;
import com.oovetest.webDemo.exception.ValidationException;
import com.oovetest.webDemo.exception.AppException;
import com.oovetest.webDemo.exception.dto.ErrorDTO;

//全局異常處理器
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorDTO> handleAppException(AppException ex) {
        HttpStatus status = switch (ex.getErrorCode()) {
            case "NOT_FOUND" -> HttpStatus.NOT_FOUND;
            case "VALIDATION_ERROR" -> HttpStatus.BAD_REQUEST;
            case "PERMISSION_DENIED" -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        return ResponseEntity.status(status)
                             .body(new ErrorDTO(ex.getErrorCode(), ex.getMessage()));
    }
}
