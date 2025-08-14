package com.cricketacademy.api.repository;

import com.cricketacademy.api.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {}
