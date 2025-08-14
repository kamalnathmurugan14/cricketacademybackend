package com.cricketacademy.api.service;

import com.cricketacademy.api.model.Attendance;
import com.cricketacademy.api.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {
    @Autowired private AttendanceRepository attendanceRepo;

    public List<Attendance> getAll() { return attendanceRepo.findAll(); }
    public Attendance add(Attendance a) { return attendanceRepo.save(a); }
    public void delete(Long id) { attendanceRepo.deleteById(id); }
    public Attendance update(Long id, Attendance a) {
        a.setId(id); return attendanceRepo.save(a);
    }
}
