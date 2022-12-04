package com.product.price.query.exception;

import static java.lang.String.valueOf;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller Advice that generates customized exceptions as endpoint responses.
 *
 * @author Germán González
 * @version 1.0
 * @since 2022-12-03
 *
 */
@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class ExceptionAdvice {

    /**
     * Controller Advice that handles when the Endpoint throws a ConstraintViolationException.
     * This scenario happens when the endpoint receives an invalid parameter.
     *
     * @param ex Exception thrown and caught
     * @return Response Entity with the exception information to be serialized by Spring Boot
     *
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(error -> {
            String[] detail = valueOf(error.getPropertyPath()).split("\\.");
            String fieldName = detail[1];
            String message = error.getMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    /**
     * Controller Advice that handles when the Endpoint throws a NotFoundException.
     * This scenario happens when the endpoint did not retrieve a product information based on the parameters got in the request.
     *
     * @param ex Exception thrown and caught
     * @return Response Entity with the exception information to be serialized by Spring Boot
     *
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        String fieldName = "priceDetail";
        String message = ex.getMessage();
        errors.put(fieldName, message);
        return new ResponseEntity<>(errors, NOT_FOUND);
    }
}
