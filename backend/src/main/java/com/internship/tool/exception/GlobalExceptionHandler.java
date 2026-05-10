package com.internship.tool.exception;

import com.internship.tool.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔴 Handle Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {

        return new ErrorResponse(
                ex.getMessage(),
                404,
                false
        );
    }

    // 🔴 Handle Validation Error
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(ValidationException ex) {

        return new ErrorResponse(
                ex.getMessage(),
                400,
                false
        );
    }

    // 🔴 Handle Generic Error
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ex) {

        ex.printStackTrace(); // 🔥 IMPORTANT

        return new ErrorResponse(
                ex.getMessage(),
                500,
                false
        );
    }
}