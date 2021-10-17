package com.senla.courses.shops.exceptionhendlers;

import com.senla.courses.shops.sevices.exceptions.JwtAuthenticationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class JwtAuthenticationExceptionHandler {

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<String> handler(JwtAuthenticationException e) {
        log.error(e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}

