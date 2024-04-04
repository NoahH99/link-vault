package com.noahhendrickson.shorten.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class InvalidRequestExceptionDTO {

    private final String message;

    @JsonIgnore
    private final HttpStatus httpStatus;

    private final int statusCode;

    private final LocalDateTime timestamp;

    public InvalidRequestExceptionDTO(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.statusCode = httpStatus.value();
        this.timestamp = LocalDateTime.now();
    }

    public InvalidRequestExceptionDTO(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
