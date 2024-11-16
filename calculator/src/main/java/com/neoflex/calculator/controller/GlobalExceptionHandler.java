package com.neoflex.calculator.controller;

import com.neoflex.calculator.exeption.ScoringExeption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
        Map<String, String> errors = new HashMap<>();
        // Извлекаем ошибки валидации
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        log.error("MethodArgumentNotValidException. {}", ex.getMessage());
        // Возвращаем ошибки с кодом 400
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ScoringExeption.class)
    public ResponseEntity<String> handleCreatedExeption(ScoringExeption ex) {
        log.error("ScoringExeption. {}", ex.getMessage());
        return new ResponseEntity<>("ScoringExeption. " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException. {}", ex.getMessage());
        return new ResponseEntity<>("HttpMessageNotReadableException. " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

