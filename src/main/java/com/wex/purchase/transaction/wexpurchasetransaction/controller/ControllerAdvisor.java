package com.wex.purchase.transaction.wexpurchasetransaction.controller;

import com.wex.purchase.transaction.wexpurchasetransaction.exception.TreasuryReportingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach((ObjectError error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            DateTimeParseException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        String fieldName = "transactionDate";
        String errorMessage = "could not parse " + ex.getParsedString() + ". Try use yyyy-MM-dd format.";
        errors.put(fieldName, errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(TreasuryReportingException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            TreasuryReportingException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        String fieldMessage = "message";
        String message = ex.getMessage();
        String fieldCause = "cause";
        String cause = String.valueOf(ex.getCause());
        errors.put(fieldMessage, message);
        errors.put(fieldCause, cause);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
