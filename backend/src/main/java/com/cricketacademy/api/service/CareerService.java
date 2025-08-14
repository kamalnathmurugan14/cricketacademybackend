package com.cricketacademy.api.service;

import com.cricketacademy.api.model.CareerApplication;
import com.cricketacademy.api.repository.CareerApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerService {
    @Autowired private CareerApplicationRepository careerRepo;

    public List<CareerApplication> getAll() { return careerRepo.findAll(); }
    public CareerApplication add(CareerApplication c) { return careerRepo.save(c); }
    public void delete(Long id) { careerRepo.deleteById(id); }
    public CareerApplication update(Long id, CareerApplication c) {
        c.setId(id); return careerRepo.save(c);
    }
}
