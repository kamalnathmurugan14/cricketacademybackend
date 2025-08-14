package com.cricketacademy.api.controller;

import com.cricketacademy.api.model.Attendance;
import com.cricketacademy.api.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/attendance")
public class AdminAttendanceController {
    @Autowired private AttendanceService attendanceService;

    @GetMapping
    public List<Attendance> getAll() { return attendanceService.getAll(); }

    @PostMapping
    public Attendance add(@RequestBody Attendance a) { return attendanceService.add(a); }

    @PutMapping("/{id}")
    public Attendance update(@PathVariable Long id, @RequestBody Attendance a) { return attendanceService.update(id, a); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { attendanceService.delete(id); }
}
