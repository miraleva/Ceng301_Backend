package com.miraleva.ceng301.exception;

import com.miraleva.ceng301.dto.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(
            org.springframework.web.HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(ApiResponse.failure("Method Not Allowed: " + ex.getMessage()),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();
        if (message != null) {
            if (message.contains("uq_enrollment")) {
                return new ResponseEntity<>(
                        ApiResponse.failure("Duplicate enrollment: Member is already enrolled in this class"),
                        HttpStatus.CONFLICT);
            }
            if (message.contains("unique") || message.contains("duplicate key")) {
                return new ResponseEntity<>(
                        ApiResponse.failure("Data integrity violation: Duplicate entry found"),
                        HttpStatus.CONFLICT);
            }
            if (message.contains("foreign key") || message.contains("constraint")
                    || message.contains("dependent records")) {
                return new ResponseEntity<>(
                        ApiResponse
                                .failure("Data integrity violation: Cannot delete record due to existing dependencies"),
                        HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(ApiResponse.failure("Database error: " + message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(ApiResponse.failure(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnsupportedOperation(UnsupportedOperationException ex) {
        return new ResponseEntity<>(ApiResponse.failure(ex.getMessage()), HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        return new ResponseEntity<>(ApiResponse.failure("Internal Server Error: " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
