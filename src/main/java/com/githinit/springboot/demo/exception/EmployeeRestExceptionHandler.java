package com.githinit.springboot.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class EmployeeRestExceptionHandler {

    // add handler for EmployeeNotFoundException

    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleException(EmployeeNotFoundException ex, WebRequest request) {

        // create error response


        ExceptionResponse error = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis());
        // return response entity


        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    //  add exception handler for other exceptions

    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleException(Exception ex, WebRequest request) {

        // create error response


        ExceptionResponse error = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis());
        // return response entity


        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
