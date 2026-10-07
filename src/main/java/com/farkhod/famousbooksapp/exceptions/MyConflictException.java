package com.farkhod.famousbooksapp.exceptions;

import org.springframework.http.HttpStatus;

public class MyConflictException extends MyException {
    public MyConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
