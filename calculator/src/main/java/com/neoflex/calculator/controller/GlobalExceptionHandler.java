package com.neoflex.calculator.controller;

import com.neoflex.calculator.exeption.ScoringExeption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity< Map<String, String>> handleHttpMessageNotReadable(MethodArgumentNotValidException ex) {

//        log.error("MethodArgumentNotValidException. {}", ex.getMessage());
//        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
//                "Incorrect JSON! HttpMessageNotReadableException " + ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        // Извлекаем ошибки валидации
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        // Возвращаем ошибки с кодом 400
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ScoringExeption.class)
    public ResponseEntity<String> handleCreatedExeption(ScoringExeption ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    protected ErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
//        log.error("HttpMessageNotReadableException. {}", ex.getMessage());
//        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
//                "Incorrect JSON! HttpMessageNotReadableException " + ex.getMessage());
//    }
}

