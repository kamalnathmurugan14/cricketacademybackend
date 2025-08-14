package com.cricketacademy.api.controller;

import com.cricketacademy.api.model.Enrollment;
import com.cricketacademy.api.service.EnrollmentService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public EnrollmentResponse enroll(@RequestBody EnrollmentRequest request) {
        Enrollment enrollment = enrollmentService.enroll(
                request.getUserId(),
                request.getProgramId(),
                request.getPaymentMethod(),
                request.getProgramTitle(),
                request.getCoachName()
        );
        return new EnrollmentResponse(enrollment.getStatus(), "Enrollment " + enrollment.getStatus());
    }

    @GetMapping("/status")
    public Enrollment getStatus(@RequestParam Long userId, @RequestParam Long programId) {
        return enrollmentService.getEnrollment(userId, programId);
    }

    @Data
    public static class EnrollmentRequest {
        private Long userId;
        private Long programId;
        private String paymentMethod;
        private String programTitle;
        private String coachName;
    }

    @Data
    public static class EnrollmentResponse {
        private String status;
        private String message;

        public EnrollmentResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
