package com.cricketacademy.api.controller;

import com.cricketacademy.api.model.Performance;
import com.cricketacademy.api.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/performance")
public class AdminPerformanceController {
    @Autowired private PerformanceService performanceService;

    @GetMapping
    public List<Performance> getAll() { return performanceService.getAll(); }

    @PostMapping
    public Performance add(@RequestBody Performance p) { return performanceService.add(p); }

    @PutMapping("/{id}")
    public Performance update(@PathVariable Long id, @RequestBody Performance p) { return performanceService.update(id, p); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { performanceService.delete(id); }
}
