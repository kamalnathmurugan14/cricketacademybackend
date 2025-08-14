package com.cricketacademy.api.service;

import com.cricketacademy.api.model.Performance;
import com.cricketacademy.api.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceService {
    @Autowired private PerformanceRepository performanceRepo;

    public List<Performance> getAll() { return performanceRepo.findAll(); }
    public Performance add(Performance p) { return performanceRepo.save(p); }
    public void delete(Long id) { performanceRepo.deleteById(id); }
    public Performance update(Long id, Performance p) {
        p.setId(id); return performanceRepo.save(p);
    }
}
