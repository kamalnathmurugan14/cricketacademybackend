package com.cricketacademy.api.repository;

import com.cricketacademy.api.entity.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
    Optional<OtpToken> findByEmailAndOtp(String email, String otp);

    Optional<OtpToken> findByEmailAndUsedFalseAndExpiresAtAfter(String email, LocalDateTime now);

    void deleteByExpiresAtBefore(LocalDateTime now);

    @Query("SELECT COUNT(o) > 0 FROM OtpToken o WHERE o.email = ?1 AND o.used = false AND o.expiresAt > ?2")
    boolean hasValidOtp(String email, LocalDateTime now);
}
