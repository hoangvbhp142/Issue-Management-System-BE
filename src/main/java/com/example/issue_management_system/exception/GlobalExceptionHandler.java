package com.example.issue_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    public ErrorResponse handleRuntimeException(WebRequest request, RuntimeException exception) {
        ErrorResponse response = new ErrorResponse();

        response.setTimestamp(new Date());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setPath(request.getDescription(false).replace("uri=", ""));
        response.setMessage(exception.getMessage());

        return response;
    }

}
