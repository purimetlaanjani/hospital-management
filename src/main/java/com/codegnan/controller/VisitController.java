package com.codegnan.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codegnan.entity.Visit;
import com.codegnan.exception.InvalidVisitIdException;
import com.codegnan.service.VisitService;

/**
 * Controller for handling HTTP requests related to Visit entity.
 * 
 * Purpose:
 * - Maps REST endpoints to VisitService.
 * - Handles CRUD operations for patient visits.
 * - Provides detailed logging for tracing request flow.
 */
@RestController
@RequestMapping("/visits")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class VisitController {

    private static final Logger log = LoggerFactory.getLogger(VisitController.class);

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    /**
     * GET /visits
     * Purpose: Retrieve all visits.
     * Logic:
     * 1. Calls service layer to fetch all visits.
     * 2. Logs total number of visits found.
     * 3. Returns HTTP 200 with list of visits.
     */
    @GetMapping("")
    public ResponseEntity<List<Visit>> getAllVisits() {
        log.info("[CONTROLLER] Fetching all visits from database");
        List<Visit> visits = visitService.findVisits();
        log.info("[CONTROLLER] Total visits retrieved: {}", visits.size());
        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    /**
     * GET /visits/{id}
     * Purpose: Retrieve a visit by ID.
     * Logic:
     * 1. Receives visit ID from path variable.
     * 2. Calls service layer to fetch visit.
     * 3. Throws InvalidVisitIdException if visit not found.
     * 4. Logs visit details and returns HTTP 200.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Visit> getVisitById(@PathVariable int id) throws InvalidVisitIdException {
        log.info("[CONTROLLER] Fetching visit with ID={}", id);
        Visit visit = visitService.findVisitById(id);
        log.info("[CONTROLLER] Visit found: ID={}, Patient={}, Doctor={}", visit.getId(), visit.getPatient().getName(), visit.getDoctor().getName());
        return new ResponseEntity<>(visit, HttpStatus.OK);
    }

    /**
     * POST /visits
     * Purpose: Create a new visit.
     * Logic:
     * 1. Receives visit object from request body.
     * 2. Calls service layer to save the visit.
     * 3. Logs visit creation with patient and doctor info.
     * 4. Returns HTTP 201 with saved visit.
     */
    @PostMapping("")
    public ResponseEntity<Visit> saveVisit(@RequestBody Visit visit) {
        log.info("[CONTROLLER] Creating new visit for Patient={} with Doctor={}", visit.getPatient().getName(), visit.getDoctor().getName());
        Visit savedVisit = visitService.saveVisit(visit);
        log.info("[CONTROLLER] Visit created successfully: ID={}", savedVisit.getId());
        return new ResponseEntity<>(savedVisit, HttpStatus.CREATED);
    }

    /**
     * PUT /visits/{id}
     * Purpose: Edit an existing visit.
     * Logic:
     * 1. Receives visit ID from path and updated visit object from body.
     * 2. Validates path ID matches body ID.
     * 3. Calls service layer to update visit.
     * 4. Logs success and returns HTTP 200 with updated visit.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Visit> editVisit(@PathVariable int id, @RequestBody Visit visit) throws InvalidVisitIdException {
        log.info("[CONTROLLER] Editing visit with ID={}", id);
        if (id != visit.getId()) {
            log.error("[CONTROLLER] Visit ID mismatch: path ID={} vs body ID={}", id, visit.getId());
            throw new InvalidVisitIdException("Visit ID in path does not match visit ID in request body");
        }
        Visit updatedVisit = visitService.editVisit(visit);
        log.info("[CONTROLLER] Visit updated successfully: ID={}, Patient={}, Doctor={}", updatedVisit.getId(), updatedVisit.getPatient().getName(), updatedVisit.getDoctor().getName());
        return new ResponseEntity<>(updatedVisit, HttpStatus.OK);
    }

    /**
     * DELETE /visits/{id}
     * Purpose: Delete a visit by ID.
     * Logic:
     * 1. Validates visit exists.
     * 2. Calls service layer to delete visit.
     * 3. Logs deleted visit details.
     * 4. Returns HTTP 200 with deleted visit.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Visit> deleteVisit(@PathVariable int id) throws InvalidVisitIdException {
        log.warn("[CONTROLLER] Request to delete visit ID={}", id);
        Visit deletedVisit = visitService.deleteVisit(id);
        log.info("[CONTROLLER] Visit deleted successfully: ID={}, Patient={}, Doctor={}", deletedVisit.getId(), deletedVisit.getPatient().getName(), deletedVisit.getDoctor().getName());
        return new ResponseEntity<>(deletedVisit, HttpStatus.OK);
    }
}
