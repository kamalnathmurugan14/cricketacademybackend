package com.cricketacademy.api.controller;

import com.cricketacademy.api.dto.PasswordResetRequest;
import com.cricketacademy.api.dto.PasswordResetResponse;
import com.cricketacademy.api.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<PasswordResetResponse> requestPasswordReset(@RequestBody PasswordResetRequest request) {
        return ResponseEntity.ok(passwordResetService.requestPasswordReset(request.getEmail()));
    }

    @PostMapping("/validate")
    public ResponseEntity<PasswordResetResponse> validateOtp(@RequestBody PasswordResetRequest request) {
        return ResponseEntity.ok(passwordResetService.validateOtp(request.getEmail(), request.getToken()));
    }

    @PostMapping("/reset")
    public ResponseEntity<PasswordResetResponse> resetPassword(@RequestBody PasswordResetRequest request) {
        return ResponseEntity.ok(passwordResetService.resetPassword(
                request.getEmail(),
                request.getNewPassword(),
                request.getNewPassword()));
    }
}
