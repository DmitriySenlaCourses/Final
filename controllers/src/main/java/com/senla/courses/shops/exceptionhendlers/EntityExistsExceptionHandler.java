package com.senla.courses.shops.exceptionhendlers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;

@ControllerAdvice
@Log4j2
public class EntityExistsExceptionHandler {

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> handler(EntityExistsException e) {
        log.info(e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
