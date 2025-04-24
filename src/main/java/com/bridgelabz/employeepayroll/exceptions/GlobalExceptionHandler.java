package com.bridgelabz.employeepayroll.exceptions;

import com.bridgelabz.employeepayroll.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String message = "Exception while processing REST request";

    /**
     * Handles invalid date format or unreadable HTTP message body.
     *
     * @param exception The thrown HttpMessageNotReadableException.
     * @return Response entity with error details and BAD_REQUEST status.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("Invalid Date Format", exception);
        ResponseDTO<String, Object> responseDTO = new ResponseDTO<>(message, "Should have data in the Format yyyy-MM-dd");
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors on method arguments annotated with @Valid.
     *
     * @param exception The thrown MethodArgumentNotValidException.
     * @return Response entity with field-specific validation messages and BAD_REQUEST status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach((fieldError) -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        ResponseDTO<String, Object> responseDTO = new ResponseDTO<>(message, errorMap);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles custom application-specific exceptions.
     *
     * @param exception The thrown EmployeePayrollException.
     * @return Response entity with custom error message and BAD_REQUEST status.
     */
    @ExceptionHandler(EmployeePayrollException.class)
    public ResponseEntity<ResponseDTO<String, Object>> handleEmployeePayrollException(EmployeePayrollException exception) {
        log.error("EmployeePayrollException: ", exception);
        ResponseDTO<String, Object> responseDTO = new ResponseDTO<>(message, exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
