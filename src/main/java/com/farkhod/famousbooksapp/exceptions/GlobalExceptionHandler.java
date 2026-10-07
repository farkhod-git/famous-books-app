package com.farkhod.famousbooksapp.exceptions;

import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    public ResponseEntity<ApiResponseDto<?>> handleException(MyException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ApiResponseDto
                .failure(ex.getStatus().value(), ex.getMessage()));
    }

}
