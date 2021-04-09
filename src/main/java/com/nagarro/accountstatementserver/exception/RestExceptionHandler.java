package com.nagarro.accountstatementserver.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebMvc
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {


    @ExceptionHandler(GenericRuntimeException.class)
    public void handleCustomException(HttpServletResponse res, GenericRuntimeException ex) throws IOException {
        log.error("Exception occurred", ex);
        res.sendError(ex
                .getHttpStatus()
                .value(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse res, Exception ex) throws IOException {
        log.error("Exception Occurred", ex);

        res.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

}
