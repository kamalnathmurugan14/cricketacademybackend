package com.cricketacademy.api.service;

import com.cricketacademy.api.model.CareerApplication;
import com.cricketacademy.api.model.CareerApplication.Status;
import com.cricketacademy.api.repository.CareerApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CareerApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(CareerApplicationService.class);

    @Autowired
    private CareerApplicationRepository careerApplicationRepository;

    public CareerApplication submitApplication(CareerApplication application) {
        logger.info("Submitting new career application for position: {}", application.getPositionType());
        try {
            CareerApplication savedApplication = careerApplicationRepository.save(application);
            logger.info("Successfully saved career application with ID: {}", savedApplication.getId());
            return savedApplication;
        } catch (Exception e) {
            logger.error("Error saving career application: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save career application", e);
        }
    }

    public List<CareerApplication> getAllApplications() {
        return careerApplicationRepository.findAll();
    }

    public List<CareerApplication> getApplicationsByStatus(Status status) {
        return careerApplicationRepository.findByStatus(status);
    }

    public CareerApplication updateApplicationStatus(Long id, Status status) {
        CareerApplication application = careerApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(status);
        return careerApplicationRepository.save(application);
    }

    public List<CareerApplication> getApprovedCoaches() {
        return careerApplicationRepository.findByStatus(Status.APPOINTED);
    }

    public CareerApplication getApplicationById(Long id) {
        return careerApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }
}
