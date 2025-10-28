package com.codegnan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base class for all people in the hospital system (Doctor, Patient).
 * We use inheritance to avoid repeating common fields like name, email, mobile.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // Each subclass has its own table
@Data // Automatically generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // Needed by JPA
@AllArgsConstructor // Constructor with all fields
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto-generated unique ID
    private int id;

    @NotBlank(message = "Name is mandatory") // Validation: cannot be null or empty
    private String name;

    @Email(message = "Email should be valid") // Validation: must be proper email format
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Mobile must be 10 digits") // Only 10-digit numbers allowed
    private String mobile;

    @NotBlank(message = "Gender is mandatory") // Cannot be null or empty
    private String gender;

    // Custom constructor for creating new objects before saving to DB (ID auto-generated)
    public Person(String name, String email, String mobile, String gender) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
    }
}
