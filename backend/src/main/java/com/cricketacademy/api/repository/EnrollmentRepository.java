package com.cricketacademy.api.repository;

import com.cricketacademy.api.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUserId(Long userId);
    Enrollment findByUserIdAndProgramId(Long userId, Long programId);
}
