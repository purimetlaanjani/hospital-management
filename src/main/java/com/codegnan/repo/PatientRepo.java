package com.codegnan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.codegnan.entity.Patient;

/**
 * Patient repository interface.
 * Handles CRUD operations for Patient entity.
 * 
 * Extending JpaRepository provides:
 * - save(), findById(), findAll(), deleteById() etc.
 * - Paging and sorting methods if needed
 */
@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {

}
