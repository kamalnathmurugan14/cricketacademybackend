package com.cricketacademy.api.dto;

import com.cricketacademy.api.model.User;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProfileUpdateRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number should be valid")
    private String phone;

    @NotNull(message = "Age is required")
    @Min(value = 5, message = "Age must be at least 5")
    @Max(value = 80, message = "Age must be at most 80")
    private Integer age;

    @NotNull(message = "Experience level is required")
    private User.ExperienceLevel experienceLevel;
}
