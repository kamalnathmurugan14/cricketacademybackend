package com.cricketacademy.api.repository;

import com.cricketacademy.api.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}
