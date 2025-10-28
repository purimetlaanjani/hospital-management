package com.codegnan.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.codegnan.exception.InvalidDateFormatException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Visit entity represents a patient's visit to a doctor.
 * Stores medical info and links to Patient and Doctor (many-to-one relationships).
 */
@Entity
@Data
@NoArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "p_id") // Foreign key to patient
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "d_id") // Foreign key to doctor
    private Doctor doctor;

    @PastOrPresent(message = "Visit date cannot be in the future")
    private Date date;

    @NotBlank(message = "Disease cannot be empty")
    private String disease;

    @Min(value = 0, message = "Weight cannot be negative")
    private double weight;

    @Min(value = 0, message = "Temperature cannot be negative")
    private double temperature;

    @Min(value = 0, message = "BP cannot be negative")
    private double bp;

    @NotBlank(message = "Mode of payment is mandatory")
    private String modeOfPayment;

    // Constructor with ID
    public Visit(int id, String strDate, String disease, double weight, double temperature, double bp,
                 String modeOfPayment) throws InvalidDateFormatException {
        this.id = id;
        this.date = parseDate(strDate);
        this.disease = disease;
        this.weight = weight;
        this.temperature = temperature;
        this.bp = bp;
        this.modeOfPayment = modeOfPayment;
    }

    // Constructor without ID (used before DB generates ID)
    public Visit(String strDate, String disease, double weight, double temperature, double bp,
                 String modeOfPayment) throws InvalidDateFormatException {
        this.date = parseDate(strDate);
        this.disease = disease;
        this.weight = weight;
        this.temperature = temperature;
        this.bp = bp;
        this.modeOfPayment = modeOfPayment;
    }

    // Convert string date to Date object
    private Date parseDate(String strDate) throws InvalidDateFormatException {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
        } catch (Exception e) {
            throw new InvalidDateFormatException(e);
        }
    }

    public void setDate(String strDate) throws InvalidDateFormatException {
        this.date = parseDate(strDate);
    }

    @Override
    public String toString() {
        String strDate = (date == null) ? "N/A" : new SimpleDateFormat("dd-MM-yyyy").format(date);
        return "Visit [id=" + id + ", date=" + strDate + ", disease=" + disease + ", weight=" + weight
                + ", temperature=" + temperature + ", bp=" + bp + ", modeOfPayment=" + modeOfPayment + "]";
    }
}

