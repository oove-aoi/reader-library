package com.oovetest.webDemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oovetest.webDemo.exception.dto.ErrorDTO;

import jakarta.validation.ConstraintViolationException;


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

    //這兩個處理驗證控制器參數時自動回拋的錯誤
    //這個處理純參數驗證未通過
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleValidation(ConstraintViolationException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorDTO("VALIDATION_ERROR", ex.getMessage()));
    }

    //這個處理DTO驗證未通過
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        //處理那個長的超醜的錯誤訊息
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return ResponseEntity.badRequest()
                .body(new ErrorDTO("VALIDATION_ERROR", message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleAll(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDTO("INTERNAL_ERROR", ex.getMessage()));
    }
}
