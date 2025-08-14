package com.cricketacademy.api.controller;

import com.cricketacademy.api.model.CareerApplication.Status;
import com.cricketacademy.api.service.CareerApplicationService;
import com.cricketacademy.api.dto.CareerRegistrationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/career/public")
public class CareerController {

    private static final Logger logger = LoggerFactory.getLogger(CareerController.class);

    @Autowired
    private CareerApplicationService careerApplicationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerApplication(
            @RequestBody com.cricketacademy.api.model.CareerApplication applicationModel) {
        try {
            // Check if user is authenticated
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAuthenticated = authentication != null &&
                    authentication.isAuthenticated() &&
                    authentication.getName() != null &&
                    !"anonymousUser".equals(authentication.getName());

            String userEmail = null;
            if (isAuthenticated && authentication != null) {
                userEmail = authentication.getName();
                logger.info("Received career registration request for position: {} from authenticated user: {}",
                        applicationModel.getPositionType(), userEmail);
            } else {
                // For public access, we'll need to extract email from formData or handle
                // differently
                logger.info("Received public career registration request for position: {}",
                        applicationModel.getPositionType());
            }

            // Validate required fields
            if (applicationModel.getPositionType() == null || applicationModel.getPositionType().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Position type is required");
            }

            // Set default status to IN_PROGRESS (equivalent to PENDING in the model)
            applicationModel.setStatus(com.cricketacademy.api.model.CareerApplication.Status.IN_PROGRESS);

            // Set applied date
            applicationModel.setAppliedDate(java.time.LocalDate.now());

            logger.info("About to save career application: positionType={}",
                    applicationModel.getPositionType());

            com.cricketacademy.api.model.CareerApplication savedApplication = careerApplicationService
                    .submitApplication(applicationModel);

            // Create success response
            CareerRegistrationResponse response = new CareerRegistrationResponse(
                    true,
                    "Career application submitted successfully!",
                    "IN_PROGRESS",
                    savedApplication.getId(),
                    "/home");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logger.error("Error processing career registration: {}", e.getMessage(), e);

            // Create error response
            CareerRegistrationResponse errorResponse = new CareerRegistrationResponse(
                    false,
                    "Failed to process career registration: " + e.getMessage(),
                    null,
                    null,
                    null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/applications")
    public ResponseEntity<List<com.cricketacademy.api.model.CareerApplication>> getAllApplications() {
        List<com.cricketacademy.api.model.CareerApplication> applications = careerApplicationService
                .getAllApplications();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/applications/pending")
    public ResponseEntity<List<com.cricketacademy.api.model.CareerApplication>> getPendingApplications() {
        List<com.cricketacademy.api.model.CareerApplication> applications = careerApplicationService
                .getApplicationsByStatus(Status.IN_PROGRESS);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/applications/approved")
    public ResponseEntity<List<com.cricketacademy.api.model.CareerApplication>> getApprovedApplications() {
        List<com.cricketacademy.api.model.CareerApplication> applications = careerApplicationService
                .getApplicationsByStatus(Status.APPOINTED);
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/applications/{id}/status")
    public ResponseEntity<?> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam Status status) {
        try {
            com.cricketacademy.api.model.CareerApplication updatedApplication = careerApplicationService
                    .updateApplicationStatus(id, status);
            return ResponseEntity.ok(updatedApplication);
        } catch (RuntimeException e) {
            logger.error("Error updating application status: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Application not found");
        }
    }

    @GetMapping("/coaches")
    public ResponseEntity<List<com.cricketacademy.api.model.CareerApplication>> getApprovedCoaches() {
        List<com.cricketacademy.api.model.CareerApplication> coaches = careerApplicationService.getApprovedCoaches();
        return ResponseEntity.ok(coaches);
    }

    @GetMapping("/applications/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable Long id) {
        try {
            com.cricketacademy.api.model.CareerApplication application = careerApplicationService
                    .getApplicationById(id);
            return ResponseEntity.ok(application);
        } catch (RuntimeException e) {
            logger.error("Error fetching application by ID: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Application not found");
        }
    }
}
