package com.cricketacademy.api.controller;

import com.cricketacademy.api.model.CoachingExpert;
import com.cricketacademy.api.model.Program;
import com.cricketacademy.api.service.CoachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Public coaching endpoints for all users: browse experts and programs.
 */
@RestController
@RequestMapping("/api/coaching")
public class CoachingController {
    @Autowired
    private CoachingService coachingService;

    @GetMapping("/experts")
    public List<CoachingExpert> listExperts() {
        return coachingService.getAllExperts();
    }

    @GetMapping("/programs")
    public List<Program> listPrograms() {
        return coachingService.getAllPrograms();
    }
}