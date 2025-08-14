package com.cricketacademy.api.controller;

import com.cricketacademy.api.model.Fee;
import com.cricketacademy.api.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/fees")
public class AdminFeeController {
    @Autowired private FeeService feeService;

    @GetMapping
    public List<Fee> getAll() { return feeService.getAll(); }

    @PostMapping
    public Fee add(@RequestBody Fee f) { return feeService.add(f); }

    @PutMapping("/{id}")
    public Fee update(@PathVariable Long id, @RequestBody Fee f) { return feeService.update(id, f); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { feeService.delete(id); }
}
