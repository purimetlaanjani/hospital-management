package com.codegnan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.codegnan.entity.Doctor;

/**
 * Doctor repository interface.
 * 
 * Responsibilities:
 * 1. Provides basic CRUD operations (Create, Read, Update, Delete) 
 *    for Doctor entities automatically because it extends JpaRepository.
 * 2. No need to write SQL queries for common operations.
 *
 * JpaRepository<Doctor, Integer> means:
 * - Doctor = entity type
 * - Integer = primary key type of Doctor
 */
@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {

}
