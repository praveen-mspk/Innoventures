package com.issuetracking.app.exception;

import com.issuetracking.app.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidStatusTransitionException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidStatus(InvalidStatusTransitionException ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ApiResponse<Object>> handleUnauthorized(UnauthorizedActionException ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(HttpStatus.FORBIDDEN.value(), ex.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ApiResponse<Object> response =
                new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Validation Failed", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", null);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

