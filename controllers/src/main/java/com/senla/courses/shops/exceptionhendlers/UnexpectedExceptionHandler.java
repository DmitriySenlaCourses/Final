package com.senla.courses.shops.exceptionhendlers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
@Log4j2
public class UnexpectedExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(Exception e) {
        Arrays.stream(e.getStackTrace()).forEach(log::error);
        return new ResponseEntity<>("Something went wrong...", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
