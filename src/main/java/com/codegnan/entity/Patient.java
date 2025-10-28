package com.codegnan.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.codegnan.exception.InvalidDateFormatException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Patient extends Person and stores patient-specific info like registration
 * date and age. Includes validation to prevent future dates or negative age.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null fields in JSON responses
public class Patient extends Person {

	@PastOrPresent(message = "Registration date cannot be in the future") // Date validation
	@JsonFormat(pattern = "yyyy-MM-dd") // JSON format for REST APIs
	private Date regDate;

	@Min(value = 0, message = "Age cannot be negative")
	private int age;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
	@JsonIgnore // Avoid recursion in JSON
	private List<Visit> visits;

	// Constructor with String date
	public Patient(int id, String name, String email, String mobile, String gender, String strRegDate, int age)
			throws InvalidDateFormatException {
		super(id, name, email, mobile, gender);
		setRegDate(strRegDate); // Convert String to Date
		this.age = age;
	}

	public Patient(String name, String email, String mobile, String gender, String strRegDate, int age)
			throws InvalidDateFormatException {
		super(name, email, mobile, gender);
		setRegDate(strRegDate);
		this.age = age;
	}

	// Helper method to get formatted date for display
	public String getRegDateFormatted() {
		if (regDate == null)
			return null;
		return new SimpleDateFormat("dd-MM-yyyy").format(regDate);
	}

	// Setter for String date input
	public void setRegDate(String strRegDate) throws InvalidDateFormatException {
		try {
			this.regDate = new SimpleDateFormat("dd-MM-yyyy").parse(strRegDate);
		} catch (Exception e) {
			throw new InvalidDateFormatException(e);
		}
	}

	@Override
	public String toString() {
		String strRegDate = (regDate == null) ? "N/A" : new SimpleDateFormat("dd-MM-yyyy").format(regDate);
		return "Patient [ " + super.toString() + " regDate=" + strRegDate + ", age=" + age + "]";
	}
}
