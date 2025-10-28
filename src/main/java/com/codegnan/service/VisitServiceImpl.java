package com.codegnan.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.codegnan.entity.Doctor;
import com.codegnan.entity.Patient;
import com.codegnan.entity.Visit;
import com.codegnan.exception.InvalidVisitIdException;
import com.codegnan.repo.VisitRepo;

/**
 * Service implementation for Visit entity.
 * 
 * Purpose: 1. Encapsulates business logic for patient visits. 2. Handles
 * creation, retrieval, update, and deletion of visits. 3. Logs service
 * operations for audit and debugging.
 */
@Service
public class VisitServiceImpl implements VisitService {

	private static final Logger log = LoggerFactory.getLogger(VisitServiceImpl.class);

	private final VisitRepo visitRepo;

	public VisitServiceImpl(VisitRepo visitRepo) {
		this.visitRepo = visitRepo;
	}

	/**
	 * Save a new visit. Purpose: Register a patient visit with a doctor.
	 */
	@Override
	public Visit saveVisit(Visit visit) {
		log.info("[SERVICE] Recording new visit: Patient={}, Doctor={}", visit.getPatient().getName(),
				visit.getDoctor().getName());
		Visit savedVisit = visitRepo.save(visit);
		log.info("[SERVICE] Visit recorded successfully. ID: {}", savedVisit.getId());
		return savedVisit;
	}

	/**
	 * Retrieve visits by patient. Purpose: Get all visits for a specific patient.
	 */
	@Override
	public List<Visit> findVisitsByPatient(Patient patient) {
		log.info("[SERVICE] Fetching visits for patient: {}", patient.getName());
		List<Visit> visits = visitRepo.findAllByPatient(patient);
		log.info("[SERVICE] Total visits retrieved for patient {}: {}", patient.getName(), visits.size());
		return visits;
	}

	/**
	 * Retrieve visits by doctor. Purpose: Get all visits handled by a specific
	 * doctor.
	 */
	@Override
	public List<Visit> findVisitsByDoctor(Doctor doctor) {
		log.info("[SERVICE] Fetching visits for doctor: {}", doctor.getName());
		List<Visit> visits = visitRepo.findAllByDoctor(doctor);
		log.info("[SERVICE] Total visits retrieved for doctor {}: {}", doctor.getName(), visits.size());
		return visits;
	}

	/**
	 * Retrieve all visits. Purpose: Get a complete list of all visits.
	 */
	@Override
	public List<Visit> findVisits() {
		log.info("[SERVICE] Fetching all visits from database");
		List<Visit> visits = visitRepo.findAll();
		log.info("[SERVICE] Total visits found: {}", visits.size());
		return visits;
	}

	/**
	 * Find a visit by ID. Purpose: Safely retrieve visit details; throw exception
	 * if not found.
	 */
	@Override
	public Visit findVisitById(int id) throws InvalidVisitIdException {
		log.info("[SERVICE] Searching for visit with ID: {}", id);
		Optional<Visit> optVisit = visitRepo.findById(id);
		Visit visit = optVisit.orElseThrow(() -> new InvalidVisitIdException("Visit ID " + id + " is invalid"));
		log.info("[SERVICE] Visit found: ID={}, Patient={}, Doctor={}", visit.getId(), visit.getPatient().getName(),
				visit.getDoctor().getName());
		return visit;
	}

	/**
	 * Edit an existing visit. Purpose: Update visit details safely.
	 */
	@Override
	public Visit editVisit(Visit visit) throws InvalidVisitIdException {
		log.info("[SERVICE] Updating visit with ID: {}", visit.getId());
		Visit existingVisit = findVisitById(visit.getId());
		existingVisit.setPatient(visit.getPatient());
		existingVisit.setDoctor(visit.getDoctor());
		Visit updatedVisit = visitRepo.save(existingVisit);
		log.info("[SERVICE] Visit updated successfully: ID={}, Patient={}, Doctor={}", updatedVisit.getId(),
				updatedVisit.getPatient().getName(), updatedVisit.getDoctor().getName());
		return updatedVisit;
	}

	/**
	 * Delete a visit by ID. Purpose: Remove a visit safely from the system.
	 */
	@Override
	public Visit deleteVisit(int id) throws InvalidVisitIdException {
		log.warn("[SERVICE] Request to delete visit with ID: {}", id);
		Visit visit = findVisitById(id);
		visitRepo.deleteById(id);
		log.info("[SERVICE] Visit deleted successfully: ID={}, Patient={}, Doctor={}", visit.getId(),
				visit.getPatient().getName(), visit.getDoctor().getName());
		return visit;
	}
}
