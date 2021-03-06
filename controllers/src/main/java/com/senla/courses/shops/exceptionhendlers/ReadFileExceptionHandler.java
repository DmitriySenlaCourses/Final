package com.senla.courses.shops.exceptionhendlers;

import com.senla.courses.shops.sevices.exceptions.ReadFileException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class ReadFileExceptionHandler {

    @ExceptionHandler(ReadFileException.class)
    public ResponseEntity<String> handler(ReadFileException e) {
        log.warn(e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
