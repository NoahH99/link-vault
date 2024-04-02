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

    public InvalidRequestExceptionDTO(String message) {
        this.message = message;
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.statusCode = httpStatus.value();
        this.timestamp = LocalDateTime.now();
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
