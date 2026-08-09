package com.internship.tool.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private String message;
    private int status;
    private boolean success;
    private LocalDateTime timestamp;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.success = false;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String message, int status, boolean success) {
        this.message = message;
        this.status = status;
        this.success = success;
        this.timestamp = LocalDateTime.now();
    }
}