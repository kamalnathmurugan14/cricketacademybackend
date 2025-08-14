package com.cricketacademy.api.service;

import com.cricketacademy.api.dto.CoachingExpertForm;
import com.cricketacademy.api.model.CoachingExpert;
import com.cricketacademy.api.model.Program;
import com.cricketacademy.api.repository.CoachingExpertRepository;
import com.cricketacademy.api.repository.ProgramRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoachingService {
    @Autowired
    private CoachingExpertRepository expertRepo;
    @Autowired
    private ProgramRepository programRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // CoachingExpert CRUD
    public List<CoachingExpert> getAllExperts() {
        return expertRepo.findAll();
    }

    // Accept raw entity (for flexibility or tests)
    public CoachingExpert addExpert(CoachingExpert e) {
        return expertRepo.save(e);
    }

    // Accept multipart form from Admin UI
    public CoachingExpert addExpert(CoachingExpertForm form) {
        CoachingExpert e = new CoachingExpert();
        e.setName(form.getName());
        e.setCoachType(form.getCoachType());
        e.setPhone(form.getPhone());
        e.setEmail(form.getEmail());
        e.setBio(form.getBio());
        e.setExperience(form.getExperience());
        e.setRating(form.getRating());
        e.setProfilePhotoUrl(form.getProfilePhotoUrl());
        // Parse qualifications JSON string -> List<String>
        List<String> quals = new ArrayList<>();
        if (form.getQualifications() != null && !form.getQualifications().isBlank()) {
            try {
                quals = objectMapper.readValue(form.getQualifications(), new TypeReference<List<String>>() {
                });
            } catch (Exception ex) {
                // fallback: treat as single qualification string
                quals = List.of(form.getQualifications());
            }
        }
        e.setQualifications(quals);
        return expertRepo.save(e);
    }

    public void deleteExpert(Long id) {
        expertRepo.deleteById(id);
    }

    public CoachingExpert updateExpert(Long id, CoachingExpert e) {
        e.setId(id);
        return expertRepo.save(e);
    }

    // Program CRUD
    public List<Program> getAllPrograms() {
        return programRepo.findAll();
    }

    public Program addProgram(Program p) {
        return programRepo.save(p);
    }

    public void deleteProgram(Long id) {
        programRepo.deleteById(id);
    }

    public Program updateProgram(Long id, Program p) {
        p.setId(id);
        return programRepo.save(p);
    }
}
