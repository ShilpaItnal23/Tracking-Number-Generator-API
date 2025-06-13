package com.example.demo.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleConstraintViolation(ConstraintViolationException ex) {
		List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> v : ex.getConstraintViolations()) {
			errors.add(v.getPropertyPath() + ": " + v.getMessage());
		}
		return Map.of("timestamp", new Date(), "status", HttpStatus.BAD_REQUEST.value(), "errors", errors);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));
		return Map.of("timestamp", new Date(), "status", HttpStatus.BAD_REQUEST.value(), "errors", errors);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		return Map.of("status", 400, "error",
				"Invalid value for parameter '" + ex.getName() + "': '" + ex.getValue() + "'", "message",
				"Expected format for '" + ex.getName() + "' is ISO 8601 (e.g. 2024-05-04T10:15:30+05:30)");
	}
}
