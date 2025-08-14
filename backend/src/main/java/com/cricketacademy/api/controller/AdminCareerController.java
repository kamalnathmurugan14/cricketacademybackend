package com.cricketacademy.api.controller;

import com.cricketacademy.api.model.CareerApplication;
import com.cricketacademy.api.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/career")
public class AdminCareerController {
    @Autowired private CareerService careerService;

    @GetMapping
    public List<CareerApplication> getAll() { return careerService.getAll(); }

    @PostMapping
    public CareerApplication add(@RequestBody CareerApplication c) { return careerService.add(c); }

    @PutMapping("/{id}")
    public CareerApplication update(@PathVariable Long id, @RequestBody CareerApplication c) { return careerService.update(id, c); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { careerService.delete(id); }
}
