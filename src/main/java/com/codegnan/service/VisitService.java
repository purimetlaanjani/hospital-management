package com.codegnan.service;

import java.util.List;
import com.codegnan.entity.Doctor;
import com.codegnan.entity.Patient;
import com.codegnan.entity.Visit;
import com.codegnan.exception.InvalidVisitIdException;

/**
 * VisitService interface.
 * 
 * Purpose:
 * - Define all operations related to patient visits.
 * - Ensures consistent contract for service layer.
 */
public interface VisitService {

    /**
     * Save a new visit.
     * @param visit - Visit object to save
     * @return saved Visit object
     */
    Visit saveVisit(Visit visit);

    /**
     * Retrieve all visits for a specific patient.
     * @param patient - Patient object
     * @return List of visits for the patient
     */
    List<Visit> findVisitsByPatient(Patient patient);

    /**
     * Retrieve all visits for a specific doctor.
     * @param doctor - Doctor object
     * @return List of visits for the doctor
     */
    List<Visit> findVisitsByDoctor(Doctor doctor);

    /**
     * Retrieve all visits in the system.
     * @return List of all visits
     */
    List<Visit> findVisits();

    /**
     * Find a visit by ID.
     * @param id - Visit ID
     * @return Visit object if found
     * @throws InvalidVisitIdException if visit ID is invalid
     */
    Visit findVisitById(int id) throws InvalidVisitIdException;

    /**
     * Edit/update an existing visit.
     * @param visit - Visit object with updated info
     * @return Updated Visit object
     * @throws InvalidVisitIdException if visit ID is invalid
     */
    Visit editVisit(Visit visit) throws InvalidVisitIdException;

    /**
     * Delete a visit by ID.
     * @param id - Visit ID
     * @return Deleted Visit object
     * @throws InvalidVisitIdException if visit ID is invalid
     */
    Visit deleteVisit(int id) throws InvalidVisitIdException;
}

