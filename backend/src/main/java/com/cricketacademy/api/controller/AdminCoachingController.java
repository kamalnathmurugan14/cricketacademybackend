package com.cricketacademy.api.controller;

import com.cricketacademy.api.dto.CoachingExpertForm;
import com.cricketacademy.api.model.CoachingExpert;
import com.cricketacademy.api.model.Program;
import com.cricketacademy.api.service.CoachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coaching")
public class AdminCoachingController {
    @Autowired
    private CoachingService coachingService;

    // --- Coaching Experts ---
    @GetMapping("/experts")
    public List<CoachingExpert> getExperts() {
        return coachingService.getAllExperts();
    }

    // Create via multipart/form-data to match Admin UI
    @PostMapping(value = "/experts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CoachingExpert addExpert(@ModelAttribute CoachingExpertForm form) {
        return coachingService.addExpert(form);
    }

    // Update via JSON body (Admin UI sends JSON on update)
    @PutMapping("/experts/{id}")
    public CoachingExpert updateExpert(@PathVariable Long id, @RequestBody CoachingExpert e) {
        return coachingService.updateExpert(id, e);
    }

    @DeleteMapping("/experts/{id}")
    public void deleteExpert(@PathVariable Long id) {
        coachingService.deleteExpert(id);
    }

    // --- Programs ---
    @GetMapping("/programs")
    public List<Program> getPrograms() {
        return coachingService.getAllPrograms();
    }

    @PostMapping("/programs")
    public Program addProgram(@RequestBody Program p) {
        return coachingService.addProgram(p);
    }

    @PutMapping("/programs/{id}")
    public Program updateProgram(@PathVariable Long id, @RequestBody Program p) {
        return coachingService.updateProgram(id, p);
    }

    @DeleteMapping("/programs/{id}")
    public void deleteProgram(@PathVariable Long id) {
        coachingService.deleteProgram(id);
    }
}
