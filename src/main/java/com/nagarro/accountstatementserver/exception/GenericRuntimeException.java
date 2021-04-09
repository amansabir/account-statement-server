package com.nagarro.accountstatementserver.exception;

import org.springframework.http.HttpStatus;

public class GenericRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -8186076742384713479L;

    private final String message;
    private final HttpStatus httpStatus;

    public GenericRuntimeException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
