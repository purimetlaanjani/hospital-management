package com.codegnan.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codegnan.entity.Doctor;
import com.codegnan.entity.Visit;
import com.codegnan.exception.InvalidDoctorIdException;
import com.codegnan.service.DoctorService;
import com.codegnan.service.VisitService;

/**
 * Controller for handling HTTP requests related to Doctor entity.
 * 
 * Purpose: - Maps REST endpoints to service layer operations. - Handles HTTP
 * GET, POST, PUT, DELETE requests. - Provides meaningful logging for tracing
 * request flow.
 */
@RestController
@RequestMapping("/doctors")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class DoctorController {

	private static final Logger log = LoggerFactory.getLogger(DoctorController.class);

	private final DoctorService doctorService;
	private final VisitService visitService;

	public DoctorController(DoctorService doctorService, VisitService visitService) {
		this.doctorService = doctorService;
		this.visitService = visitService;
	}

	/**
	 * GET /doctors Purpose: Retrieve all doctors from the system. Logic: 1. Calls
	 * service layer to fetch list of doctors. 2. Logs total number of doctors
	 * retrieved. 3. Returns HTTP 200 with list of doctors.
	 */
	@GetMapping("")
	public ResponseEntity<List<Doctor>> getAllDoctors() {
		log.info("[CONTROLLER] Request to fetch all doctors received");
		List<Doctor> doctors = doctorService.findAllDoctors();
		log.info("[CONTROLLER] Successfully retrieved {} doctors", doctors.size());
		return ResponseEntity.ok(doctors);
	}

	/**
	 * GET /doctors/{id} Purpose: Retrieve doctor by ID. Logic: 1. Receives doctor
	 * ID from path. 2. Calls service layer to fetch doctor. 3. Throws
	 * InvalidDoctorIdException if doctor not found. 4. Logs result and returns HTTP
	 * 200 with doctor data.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Doctor> getDoctorById(@PathVariable int id) throws InvalidDoctorIdException {
		log.info("[CONTROLLER] Fetching doctor details for ID={}", id);
		Doctor doctor = doctorService.findDoctorById(id);
		log.info("[CONTROLLER] Doctor found: ID={}, Name={}", doctor.getId(), doctor.getName());
		return ResponseEntity.ok(doctor);
	}

	/**
	 * GET /doctors/{id}/visits Purpose: Retrieve all visits associated with a
	 * doctor. Logic: 1. Fetch doctor by ID (validates existence). 2. Fetch visits
	 * from VisitService for this doctor. 3. Logs the number of visits found. 4.
	 * Returns HTTP 200 with visit list.
	 */
	@GetMapping("/{id}/visits")
	public ResponseEntity<List<Visit>> getVisitsByDoctor(@PathVariable int id) throws InvalidDoctorIdException {
		log.info("[CONTROLLER] Fetching visits for doctor ID={}", id);
		Doctor doctor = doctorService.findDoctorById(id);
		List<Visit> visits = visitService.findVisitsByDoctor(doctor);
		log.info("[CONTROLLER] Total visits retrieved for Doctor ID={} ({}): {}", doctor.getId(), doctor.getName(),
				visits.size());
		return ResponseEntity.ok(visits);
	}

	/**
	 * POST /doctors Purpose: Register a new doctor. Logic: 1. Receives doctor
	 * object in request body. 2. Calls service layer to save doctor. 3. Logs
	 * success with doctor ID. 4. Returns HTTP 201 (Created) with saved doctor.
	 */
	@PostMapping("")
	public ResponseEntity<Doctor> saveDoctor(@RequestBody Doctor doctor) {
		log.info("[CONTROLLER] Creating a new doctor with name={}", doctor.getName());
		Doctor savedDoctor = doctorService.hireDoctor(doctor);
		log.info("[CONTROLLER] Doctor created successfully: ID={}, Name={}", savedDoctor.getId(),
				savedDoctor.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
	}

	/**
	 * PUT /doctors/{id} Purpose: Update an existing doctor's details. Logic: 1.
	 * Receives doctor ID from path and updated doctor object from body. 2.
	 * Validates that path ID matches body ID. 3. Calls service to update doctor. 4.
	 * Logs success and returns HTTP 200 with updated doctor.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable int id, @RequestBody Doctor doctor)
			throws InvalidDoctorIdException {
		log.info("[CONTROLLER] Updating doctor with ID={}", id);
		if (id != doctor.getId()) {
			log.error("[CONTROLLER] Doctor ID mismatch: path ID={} vs body ID={}", id, doctor.getId());
			throw new InvalidDoctorIdException("Doctor ID in path does not match doctor ID in request body");
		}
		Doctor updatedDoctor = doctorService.updateDoctor(doctor);
		log.info("[CONTROLLER] Doctor updated successfully: ID={}, Name={}", updatedDoctor.getId(),
				updatedDoctor.getName());
		return ResponseEntity.ok(updatedDoctor);
	}

	/**
	 * DELETE /doctors/{id} Purpose: Delete a doctor by ID. Logic: 1. Validates
	 * doctor exists. 2. Deletes doctor using service layer. 3. Logs deletion and
	 * returns HTTP 200 with deleted doctor data.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Doctor> deleteDoctor(@PathVariable int id) throws InvalidDoctorIdException {
		log.warn("[CONTROLLER] Request received to delete doctor ID={}", id);
		Doctor deletedDoctor = doctorService.deleteDoctor(id);
		log.info("[CONTROLLER] Doctor deleted successfully: ID={}, Name={}", deletedDoctor.getId(),
				deletedDoctor.getName());
		return ResponseEntity.ok(deletedDoctor);
	}
}
