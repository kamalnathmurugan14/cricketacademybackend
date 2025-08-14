package com.cricketacademy.api.repository;

import com.cricketacademy.api.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {}
