package com.codegnan.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codegnan.entity.Doctor;
import com.codegnan.exception.InvalidDoctorIdException;
import com.codegnan.repo.DoctorRepo;

/**
 * Service implementation for Doctor entity.
 * 
 * Purpose: 1. Encapsulates all business logic related to doctors. 2. Ensures
 * validation before update/delete operations. 3. Separates business logic from
 * controller layer. 4. Logs important service-level actions for
 * auditing/debugging.
 */
@Service
public class DoctorServiceImpl implements DoctorService {

	// Service-level logger for Doctor operations
	private static final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);

	private final DoctorRepo doctorRepo;

	public DoctorServiceImpl(DoctorRepo doctorRepo) {
		this.doctorRepo = doctorRepo;
	}

	/**
	 * Hire a new doctor. Purpose: Add a doctor to the system and generate an ID.
	 * 
	 * @param doctor - Doctor object to save
	 * @return saved Doctor with auto-generated ID
	 */
	@Override
	public Doctor hireDoctor(Doctor doctor) {
		log.info("[SERVICE] Starting process to hire a new doctor: {}", doctor.getName());
		Doctor savedDoctor = doctorRepo.save(doctor); // Persist doctor in DB
		log.info("[SERVICE] Doctor hired successfully with ID: {}", savedDoctor.getId());
		return savedDoctor;
	}

	/**
	 * Find a doctor by ID. Purpose: Retrieve doctor details safely; throw exception
	 * if not found.
	 * 
	 * @param id - Doctor ID to fetch
	 * @return Doctor object if found
	 * @throws InvalidDoctorIdException if doctor does not exist
	 */
	@Override
	public Doctor findDoctorById(int id) throws InvalidDoctorIdException {
		log.info("[SERVICE] Attempting to retrieve doctor with ID: {}", id);
		Optional<Doctor> optDoctor = doctorRepo.findById(id);
		Doctor doctor = optDoctor
				.orElseThrow(() -> new InvalidDoctorIdException("Doctor with ID " + id + " does not exist"));
		log.info("[SERVICE] Doctor retrieval successful: {}", doctor.getName());
		return doctor;
	}

	/**
	 * List all doctors. Purpose: Provide a complete list of all registered doctors
	 * in the system.
	 * 
	 * @return List of all doctors
	 */
	@Override
	public List<Doctor> findAllDoctors() {
		log.info("[SERVICE] Fetching list of all doctors");
		List<Doctor> doctors = doctorRepo.findAll();
		log.info("[SERVICE] Total doctors retrieved: {}", doctors.size());
		return doctors;
	}

	/**
	 * Update doctor details. Purpose: Modify existing doctor information safely.
	 * Steps: 1. Validate that the doctor exists. 2. Update the fields. 3. Save the
	 * updated doctor.
	 * 
	 * @param doctor - Doctor object with updated data
	 * @return updated Doctor object
	 * @throws InvalidDoctorIdException if doctor ID is invalid
	 */
	@Override
	public Doctor updateDoctor(Doctor doctor) throws InvalidDoctorIdException {
		log.info("[SERVICE] Initiating update for doctor ID: {}", doctor.getId());

		// Validate existence
		Doctor existingDoctor = findDoctorById(doctor.getId());

		// Update fields
		existingDoctor.setName(doctor.getName());
		existingDoctor.setSpecialization(doctor.getSpecialization());
		existingDoctor.setExperience(doctor.getExperience());
		existingDoctor.setDegrees(doctor.getDegrees());
		existingDoctor.setSalary(doctor.getSalary());
		existingDoctor.setEmail(doctor.getEmail());

		Doctor updatedDoctor = doctorRepo.save(existingDoctor);
		log.info("[SERVICE] Doctor updated successfully: ID={}, Name={}", updatedDoctor.getId(),
				updatedDoctor.getName());
		return updatedDoctor;
	}

	/**
	 * Delete a doctor. Purpose: Safely remove a doctor from the system. Steps: 1.
	 * Validate that the doctor exists. 2. Delete from the database. 3. Log the
	 * operation.
	 * 
	 * @param id - Doctor ID to delete
	 * @return Deleted Doctor object
	 * @throws InvalidDoctorIdException if doctor ID is invalid
	 */
	@Override
	public Doctor deleteDoctor(int id) throws InvalidDoctorIdException {
		log.warn("[SERVICE] Request received to delete doctor with ID: {}", id);

		// Validate existence
		Doctor doctor = findDoctorById(id);

		// Delete doctor
		doctorRepo.deleteById(id);
		log.info("[SERVICE] Doctor deleted successfully: ID={}, Name={}", doctor.getId(), doctor.getName());

		return doctor;
	}
}

