package com.codegnan.service;

import java.util.List;

import com.codegnan.entity.Doctor;
import com.codegnan.exception.InvalidDoctorIdException;

/**
 * DoctorService interface defines the business operations
 * related to Doctor entities.
 * 
 * Purpose:
 * - Decouples business logic from controllers and repository.
 * - Ensures proper exception handling and validation.
 */
public interface DoctorService {

    /**
     * Hire or register a new doctor.
     * @param doctor - doctor details to save
     * @return saved doctor with generated ID
     */
    Doctor hireDoctor(Doctor doctor);

    /**
     * Fetch a doctor by ID.
     * @param id - doctor ID
     * @return doctor object if found
     * @throws InvalidDoctorIdException if ID is invalid
     */
    Doctor findDoctorById(int id) throws InvalidDoctorIdException;

    /**
     * Get a list of all doctors.
     * @return list of doctors
     */
    List<Doctor> findAllDoctors();

    /**
     * Update details of an existing doctor.
     * @param doctor - updated doctor object
     * @return updated doctor
     * @throws InvalidDoctorIdException if doctor does not exist
     */
    Doctor updateDoctor(Doctor doctor) throws InvalidDoctorIdException;

    /**
     * Delete a doctor by ID.
     * @param id - doctor ID to delete
     * @return deleted doctor object
     * @throws InvalidDoctorIdException if doctor does not exist
     */
    Doctor deleteDoctor(int id) throws InvalidDoctorIdException;
}
