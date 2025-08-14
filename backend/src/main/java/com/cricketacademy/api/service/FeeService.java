package com.cricketacademy.api.service;

import com.cricketacademy.api.model.Fee;
import com.cricketacademy.api.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeService {
    @Autowired private FeeRepository feeRepo;

    public List<Fee> getAll() { return feeRepo.findAll(); }
    public Fee add(Fee f) { return feeRepo.save(f); }
    public void delete(Long id) { feeRepo.deleteById(id); }
    public Fee update(Long id, Fee f) {
        f.setId(id); return feeRepo.save(f);
    }
}
