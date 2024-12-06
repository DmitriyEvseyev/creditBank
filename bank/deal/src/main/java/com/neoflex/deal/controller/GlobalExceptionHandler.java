package com.neoflex.deal.controller;

import com.neoflex.deal.exeptions.NotFoundException;
import com.neoflex.deal.model.entities.Statement;
import com.neoflex.deal.services.StatementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.neoflex.deal.model.enumFilds.ApplicationStatusEnum.CC_DENIED;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final StatementService statementService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        // Извлекаем ошибки валидации
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), "MethodArgumentNotValidException. "
                        + error.getDefaultMessage())
                );
        log.error("MethodArgumentNotValidException. {}", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException. {}", ex.getMessage());
        return new ResponseEntity<>("HttpMessageNotReadableException. " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.error("NotFoundException. {} ", e.getMessage());
        return new ResponseEntity<>("NotFoundException. " + e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleCreatedExeption(HttpClientErrorException ex, HttpServletRequest request) {
        String URI = request.getRequestURI();
        String[] parts = URI.split("/");
        String uuid = parts[parts.length - 1];
        UUID statementId = UUID.fromString(uuid);
        log.info("statementId - {}", statementId);

        Statement statement = statementService.getStatement(statementId);
        statement.setApplicationStatusEnum(CC_DENIED);
        Statement statementUpdate = statementService.updateStatement(statement, Timestamp.valueOf(LocalDateTime.now()));
        log.info("statementUpdate - {}", statementUpdate);

        log.error("HttpClientErrorException. {}", ex.getMessage());
        return new ResponseEntity<>("HttpClientErrorException. " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

