package com.codegnan.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codegnan.entity.Patient;
import com.codegnan.entity.Visit;
import com.codegnan.exception.InvalidPatientIdException;
import com.codegnan.service.PatientService;
import com.codegnan.service.VisitService;

/**
 * Controller for handling HTTP requests related to Patient entity.
 * 
 * Purpose: - Maps REST endpoints to PatientService and VisitService. - Handles
 * CRUD operations for patients and retrieving visits for a patient. - Provides
 * detailed logging for tracing request flow.
 */
@RestController
@RequestMapping("/patients")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class PatientController {

	private static final Logger log = LoggerFactory.getLogger(PatientController.class);

	private final PatientService patientService;
	private final VisitService visitService;

	public PatientController(PatientService patientService, VisitService visitService) {
		this.patientService = patientService;
		this.visitService = visitService;
	}

	/**
	 * GET /patients Purpose: Retrieve all patients in the system. Logic: 1. Calls
	 * service layer to fetch all patients. 2. Logs total number of patients found.
	 * 3. Returns HTTP 200 with list of patients.
	 */
	@GetMapping("")
	public ResponseEntity<List<Patient>> getAllPatients() {
		log.info("[CONTROLLER] Fetching all patients from database");
		List<Patient> patients = patientService.findAllPatients();
		log.info("[CONTROLLER] Total patients retrieved: {}", patients.size());
		return new ResponseEntity<>(patients, HttpStatus.OK);
	}

	/**
	 * GET /patients/{id} Purpose: Retrieve a patient by ID. Logic: 1. Receives
	 * patient ID from path variable. 2. Calls service to fetch patient by ID. 3.
	 * Throws InvalidPatientIdException if not found. 4. Logs patient details and
	 * returns HTTP 200.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable int id) throws InvalidPatientIdException {
		log.info("[CONTROLLER] Fetching patient with ID={}", id);
		Patient patient = patientService.findPatientById(id);
		log.info("[CONTROLLER] Patient found: ID={}, Name={}", patient.getId(), patient.getName());
		return new ResponseEntity<>(patient, HttpStatus.OK);
	}

	/**
	 * GET /patients/{id}/visits Purpose: Retrieve all visits associated with a
	 * patient. Logic: 1. Fetch patient by ID (validates existence). 2. Calls
	 * VisitService to fetch visits for this patient. 3. Logs total visits found. 4.
	 * Returns HTTP 200 with visit list.
	 */
	@GetMapping("/{id}/visits")
	public ResponseEntity<List<Visit>> getVisitsByPatient(@PathVariable int id) throws InvalidPatientIdException {
		log.info("[CONTROLLER] Fetching visits for patient ID={}", id);
		Patient patient = patientService.findPatientById(id);
		List<Visit> visits = visitService.findVisitsByPatient(patient);
		log.info("[CONTROLLER] Total visits retrieved for Patient ID={} ({}): {}", patient.getId(), patient.getName(),
				visits.size());
		return new ResponseEntity<>(visits, HttpStatus.OK);
	}

	/**
	 * POST /patients Purpose: Register/add a new patient. Logic: 1. Receives
	 * patient object from request body. 2. Calls service to save the patient. 3.
	 * Logs success with patient ID. 4. Returns HTTP 201 with saved patient.
	 */
	@PostMapping("")
	public ResponseEntity<Patient> addNewPatient(@RequestBody Patient patient) {
		log.info("[CONTROLLER] Creating a new patient with name={}", patient.getName());
		Patient savedPatient = patientService.savePatient(patient);
		log.info("[CONTROLLER] Patient created successfully: ID={}, Name={}", savedPatient.getId(),
				savedPatient.getName());
		return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
	}

	/**
	 * PUT /patients/{id} Purpose: Update an existing patient's details. Logic: 1.
	 * Receives patient ID from path and patient object from body. 2. Validates that
	 * path ID matches body ID. 3. Calls service to update patient. 4. Logs updated
	 * patient details. 5. Returns HTTP 200 with updated patient.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Patient> editPatient(@PathVariable int id, @RequestBody Patient patient)
			throws InvalidPatientIdException {
		log.info("[CONTROLLER] Updating patient with ID={}", id);
		if (id != patient.getId()) {
			log.error("[CONTROLLER] Patient ID mismatch: path ID={} vs body ID={}", id, patient.getId());
			throw new InvalidPatientIdException("Patient ID in path does not match patient ID in request body");
		}
		Patient updatedPatient = patientService.updatePatient(patient);
		log.info("[CONTROLLER] Patient updated successfully: ID={}, Name={}", updatedPatient.getId(),
				updatedPatient.getName());
		return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
	}

	/**
	 * DELETE /patients/{id} Purpose: Delete a patient by ID. Logic: 1. Validates
	 * patient exists. 2. Calls service to delete patient. 3. Logs deleted patient
	 * details. 4. Returns HTTP 200 with deleted patient data.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Patient> deletePatient(@PathVariable int id) throws InvalidPatientIdException {
		log.warn("[CONTROLLER] Request to delete patient ID={}", id);
		Patient deletedPatient = patientService.deletePatient(id);
		log.info("[CONTROLLER] Patient deleted successfully: ID={}, Name={}", deletedPatient.getId(),
				deletedPatient.getName());
		return new ResponseEntity<>(deletedPatient, HttpStatus.OK);
	}
}
