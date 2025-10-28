package com.codegnan.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global Exception Handler for Hospital Management Application
 */
@RestControllerAdvice
public class HospitalGlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(HospitalGlobalExceptionHandler.class);

	// Doctor Not Found
	@ExceptionHandler(InvalidDoctorIdException.class)
	public ResponseEntity<ErrorAPI> handleInvalidDoctorId(InvalidDoctorIdException ex) {
		log.error("InvalidDoctorIdException: {}", ex.getMessage());
		ErrorAPI error = new ErrorAPI();
		error.setLocalDateTime(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		error.setError("Doctor Not Found");
		error.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	// Patient Not Found
	@ExceptionHandler(InvalidPatientIdException.class)
	public ResponseEntity<ErrorAPI> handleInvalidPatientId(InvalidPatientIdException ex) {
		log.error("InvalidPatientIdException: {}", ex.getMessage());
		ErrorAPI error = new ErrorAPI();
		error.setLocalDateTime(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		error.setError("Patient Not Found");
		error.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	// Visit Not Found
	@ExceptionHandler(InvalidVisitIdException.class)
	public ResponseEntity<ErrorAPI> handleInvalidVisitId(InvalidVisitIdException ex) {
		log.error("InvalidVisitIdException: {}", ex.getMessage());
		ErrorAPI error = new ErrorAPI();
		error.setLocalDateTime(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		error.setError("Visit Not Found");
		error.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	// Invalid Date Format
	@ExceptionHandler(InvalidDateFormatException.class)
	public ResponseEntity<ErrorAPI> handleInvalidDateFormat(InvalidDateFormatException ex) {
		log.error("InvalidDateFormatException: {}", ex.getMessage());
		ErrorAPI error = new ErrorAPI();
		error.setLocalDateTime(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		error.setError("Invalid Date Format");
		error.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// Validation Errors (e.g., Bean Validation @NotNull, @Size, etc.)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorAPI> handleValidationErrors(MethodArgumentNotValidException ex) {
		String errors = ex.getBindingResult().getFieldErrors().stream()
				.map(obj -> obj.getField() + ": " + obj.getDefaultMessage()).collect(Collectors.joining(", "));

		ErrorAPI error = new ErrorAPI();
		error.setLocalDateTime(LocalDateTime.now());
		error.setMessage(errors);
		error.setError("Validation Failed");
		error.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// Malformed JSON
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorAPI> handleMalformedJson(HttpMessageNotReadableException ex) {
		log.error("Malformed JSON: {}", ex.getMessage());
		ErrorAPI error = new ErrorAPI();
		error.setLocalDateTime(LocalDateTime.now());
		error.setMessage("Malformed JSON request");
		error.setError("Invalid Input");
		error.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// Catch All Other Exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorAPI> handleGeneralError(Exception ex) {
		log.error("Internal Server Error: {}", ex.getMessage(), ex);
		ErrorAPI error = new ErrorAPI();
		error.setLocalDateTime(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		error.setError("Unexpected Error");
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
