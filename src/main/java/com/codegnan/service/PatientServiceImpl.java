package com.codegnan.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.codegnan.entity.Patient;
import com.codegnan.exception.InvalidPatientIdException;
import com.codegnan.repo.PatientRepo;

/**
 * Service implementation for Patient entity.
 * 
 * Purpose: 1. Encapsulates business logic related to patients. 2. Ensures
 * validation before update/delete. 3. Logs service-level operations for
 * debugging and auditing.
 */
@Service
public class PatientServiceImpl implements PatientService {

	private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

	private final PatientRepo patientRepo;

	public PatientServiceImpl(PatientRepo patientRepo) {
		this.patientRepo = patientRepo;
	}

	/**
	 * Save a new patient to the database. Purpose: Register a patient and generate
	 * a unique ID.
	 */
	@Override
	public Patient savePatient(Patient patient) {
		log.info("[SERVICE] Registering new patient: {}", patient.getName());
		Patient savedPatient = patientRepo.save(patient);
		log.info("[SERVICE] Patient registration successful. ID: {}", savedPatient.getId());
		return savedPatient;
	}

	/**
	 * Fetch a patient by ID. Purpose: Retrieve patient safely; throw exception if
	 * ID invalid.
	 */
	@Override
	public Patient findPatientById(int id) throws InvalidPatientIdException {
		log.info("[SERVICE] Looking for patient with ID: {}", id);
		Optional<Patient> optPatient = patientRepo.findById(id);
		Patient patient = optPatient
				.orElseThrow(() -> new InvalidPatientIdException("Patient with ID " + id + " does not exist"));
		log.info("[SERVICE] Patient retrieval successful: {}", patient.getName());
		return patient;
	}

	/**
	 * Retrieve all patients. Purpose: Provide a complete list of registered
	 * patients.
	 */
	@Override
	public List<Patient> findAllPatients() {
		log.info("[SERVICE] Fetching all patients from the database");
		List<Patient> patients = patientRepo.findAll();
		log.info("[SERVICE] Total patients retrieved: {}", patients.size());
		return patients;
	}

	/**
	 * Update patient details. Purpose: Modify an existing patient's information
	 * safely. Steps: 1. Validate patient existence. 2. Update fields. 3. Save the
	 * updated patient.
	 */
	@Override
	public Patient updatePatient(Patient patient) throws InvalidPatientIdException {
		log.info("[SERVICE] Updating patient with ID: {}", patient.getId());

		Patient existingPatient = findPatientById(patient.getId());
		existingPatient.setName(patient.getName());
		existingPatient.setAge(patient.getAge());
		existingPatient.setGender(patient.getGender());
		existingPatient.setMobile(patient.getMobile());
		existingPatient.setEmail(patient.getEmail());

		Patient updatedPatient = patientRepo.save(existingPatient);
		log.info("[SERVICE] Patient updated successfully: ID={}, Name={}", updatedPatient.getId(),
				updatedPatient.getName());
		return updatedPatient;
	}

	/**
	 * Delete a patient. Purpose: Safely remove a patient from the system. Steps: 1.
	 * Validate existence. 2. Delete from DB. 3. Log operation.
	 */
	@Override
	public Patient deletePatient(int id) throws InvalidPatientIdException {
		log.warn("[SERVICE] Request to delete patient with ID: {}", id);

		Patient patient = findPatientById(id);
		patientRepo.deleteById(id);

		log.info("[SERVICE] Patient deleted successfully: ID={}, Name={}", patient.getId(), patient.getName());
		return patient;
	}
}
