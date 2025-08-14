package com.cricketacademy.api.repository;

import com.cricketacademy.api.model.CareerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CareerApplicationRepository extends JpaRepository<CareerApplication, Long> {
    List<CareerApplication> findByStatus(CareerApplication.Status status);
}
