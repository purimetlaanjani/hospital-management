package com.codegnan.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.codegnan.entity.Doctor;
import com.codegnan.entity.Patient;
import com.codegnan.entity.Visit;

/**
 * Visit repository interface.
 * Handles CRUD operations for Visit entity.
 *
 * Custom methods:
 * 1. findAllByPatient(Patient patient)
 *    - Finds all visits for a specific patient
 *    - Spring Data JPA automatically generates query from method name
 * 
 * 2. findAllByDoctor(Doctor doctor)
 *    - Finds all visits for a specific doctor
 *
 * Note: You can also write custom JPQL queries using @Query
 * Example (commented out):
 * @Query("SELECT v FROM Visit v WHERE v.date >= :fromDate AND v.date <= :toDate")
 * public List<Visit> findAllByPatientBetweenDates(Patient patient, Date fromDate, Date toDate);
 */
@Repository
public interface VisitRepo extends JpaRepository<Visit, Integer> {
    public List<Visit> findAllByPatient(Patient patient);
    public List<Visit> findAllByDoctor(Doctor doctor);
}
