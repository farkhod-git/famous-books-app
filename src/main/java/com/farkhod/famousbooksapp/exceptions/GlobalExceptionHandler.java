package com.farkhod.famousbooksapp.exceptions;

import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    public ResponseEntity<ApiResponseDto<?>> handleException(MyException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(ApiResponseDto
                .failure(ex.getStatus().value(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<?>> handleException(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        for (FieldError fe : ex.getFieldErrors()) {
            sb.append(fe.getDefaultMessage())
                    .append("\n");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.failure(400, sb.toString()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponseDto<?>> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.failure(500, ex.getMessage()));
    }

}
