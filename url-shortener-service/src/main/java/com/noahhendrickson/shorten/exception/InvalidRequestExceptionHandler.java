package com.noahhendrickson.shorten.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidRequestExceptionHandler {

    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<InvalidRequestExceptionDTO> handleInvalidRequestException(InvalidRequestException exception) {
        InvalidRequestExceptionDTO exceptionDTO = new InvalidRequestExceptionDTO(exception.getMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionDTO.getHttpStatus());
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<InvalidRequestExceptionDTO> handleInvalidRequestException(HttpMessageNotReadableException exception) {
        InvalidRequestExceptionDTO exceptionDTO = new InvalidRequestExceptionDTO(exception.getMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionDTO.getHttpStatus());
    }
}
