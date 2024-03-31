package com.noahhendrickson.backend.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class InvalidRequestExceptionDTO {

    private final String message;
    private final HttpStatus statusCode;
    private final LocalDateTime timestamp;

    public InvalidRequestExceptionDTO(String message) {
        this.message = message;
        this.statusCode = HttpStatus.BAD_REQUEST;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
