package com.cricketacademy.api.service;

import com.cricketacademy.api.model.Enrollment;
import com.cricketacademy.api.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Enrollment enroll(Long userId, Long programId, String paymentMethod, String programTitle, String coachName) {
        String status = paymentMethod.equalsIgnoreCase("cash") ? "pending" : "enrolled";
        Enrollment enrollment = Enrollment.builder()
                .userId(userId)
                .programId(programId)
                .paymentMethod(paymentMethod)
                .status(status)
                .programTitle(programTitle)
                .coachName(coachName)
                .build();
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment getEnrollment(Long userId, Long programId) {
        return enrollmentRepository.findByUserIdAndProgramId(userId, programId);
    }
}
