package com.codegnan.service;

import java.util.List;
import com.codegnan.entity.Patient;
import com.codegnan.exception.InvalidPatientIdException;

/**
 * PatientService interface.
 * 
 * Purpose:
 * - Define all business operations related to Patient entity.
 * - Acts as a contract for service implementation.
 */
public interface PatientService {

    /**
     * Save/register a new patient.
     * @param patient - Patient object to save
     * @return saved Patient object
     */
    Patient savePatient(Patient patient);

    /**
     * Find patient by ID.
     * @param id - Patient ID
     * @return Patient object if found
     * @throws InvalidPatientIdException if patient does not exist
     */
    Patient findPatientById(int id) throws InvalidPatientIdException;

    /**
     * Retrieve all patients.
     * @return List of all patients
     */
    List<Patient> findAllPatients();

    /**
     * Update patient details.
     * @param patient - Patient object with updated info
     * @return updated Patient object
     * @throws InvalidPatientIdException if patient ID is invalid
     */
    Patient updatePatient(Patient patient) throws InvalidPatientIdException;

    /**
     * Delete a patient by ID.
     * @param id - Patient ID to delete
     * @return Deleted Patient object
     * @throws InvalidPatientIdException if patient ID is invalid
     */
    Patient deletePatient(int id) throws InvalidPatientIdException;
}
