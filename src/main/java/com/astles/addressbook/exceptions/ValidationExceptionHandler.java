package com.astles.addressbook.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<Map<String, List<String>>> handle(RuntimeException ex /*, HttpHeaders headers,
                                                 HttpStatus status, WebRequest request*/) {
    Map<String, List<String>> body = new HashMap<>();
    List<String> errors;
    if (ex instanceof ConstraintViolationException) {
      errors = ((ConstraintViolationException) ex).getConstraintViolations()
              .stream()
              .map(e -> e.getPropertyPath() + ": " + e.getMessage())
              .collect(Collectors.toList());
    } else if (ex instanceof TransactionSystemException) {
      errors = List.of(((TransactionSystemException) ex).getMessage());
    } else {
      errors = null;
    }


    body.put("errors", errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}
