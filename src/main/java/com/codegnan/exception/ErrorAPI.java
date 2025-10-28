package com.codegnan.exception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Standard Error response for API
 */
@Getter
@Setter
public class ErrorAPI {
	private String message; // Actual error message
	private String status; // HTTP status
	private String error; // Error type
	private LocalDateTime localDateTime; // Timestamp
}
