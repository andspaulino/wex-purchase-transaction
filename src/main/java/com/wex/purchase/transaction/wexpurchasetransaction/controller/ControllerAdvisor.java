package com.wex.purchase.transaction.wexpurchasetransaction.controller;

import com.wex.purchase.transaction.wexpurchasetransaction.exception.TreasuryReportingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        Map<String, Object> error = new LinkedHashMap<>();
        Map<String, String> errors = new LinkedHashMap<>();
        error.put("statusCode", HttpStatus.BAD_REQUEST.value());
        error.put("errorMessage", "Error occurred while trying to validade fields.");

        ex.getBindingResult()
                .getAllErrors()
                .forEach((ObjectError objectError) -> {
                    String fieldName = ((FieldError) objectError).getField();
                    String errorMessage = objectError.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        error.put("validationErrors", errors);


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(TreasuryReportingException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            TreasuryReportingException ex
    ) {
        Map<String, Object> errors = new LinkedHashMap<>();

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        if(ex.getCause() instanceof HttpStatusCodeException) {
            HttpStatusCodeException cause = (HttpStatusCodeException) ex.getCause();
            errors.put("statusCode", cause.getStatusCode().value());
            errors.put("errorMessage", ex.getMessage());
            errors.put("errorResponseBody", cause.getResponseBodyAsString());

            httpStatus = cause.getStatusCode();
        } else {
            errors.put("statusCode", httpStatus.value());
            errors.put("errorMessage", ex.getMessage());
        }

        return ResponseEntity.status(httpStatus).body(errors);
    }
}
